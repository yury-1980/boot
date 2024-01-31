package ru.clevertec.aspect;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.clevertec.cache.LRUCache;
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.entity.Person;
import ru.clevertec.exeption.EntityNotFoundExeption;
import ru.clevertec.mapper.PersonMapper;
import ru.clevertec.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class CachePersonAspect {

    private LRUCache<UUID, Person> cache;
    private PersonRepository personRepository;
    private PersonMapper personMapper;

    @PostConstruct
    void init() {
        log.info(cache.toString());
    }

    /**
     * Возвращает объект из кеша, если нет, то читает из базы.
     *
     * @param joinPoint Точка внедрения кода в метод PersonServiceImpl.findByUUID()
     * @return ResponsePersonDTO.
     * @throws EntityNotFoundExeption если не найден.
     */
    @Around("execution(* ru.clevertec.service.impl.PersonServiceImpl.findByUUID(..))")
    public ResponsePersonDTO aroundfindByUUID(ProceedingJoinPoint joinPoint) {
        log.info("Вошли в аспект GET !");
        Object[] args = joinPoint.getArgs();
        UUID uuid = (UUID) args[0];
        Person person = cache.get(uuid);

        if (person != null) {
            log.info("Объект в кеше найден!");
            return personMapper.toResponsePersonDto(person);

        } else {
            log.info("Объект взят из базы !");
            return personRepository.findByUuid(uuid).stream()
                    .peek(person1 -> cache.put(person1.getUuid(), person1))
                    .map(person1 -> personMapper.toResponsePersonDto(person1))
                    .findFirst()
                    .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        }
    }

    /**
     * Создаёт объект Person из объекта RequestPersonDTO. И помещает в БД, а затем в кеш.
     *
     * @param joinPoint Точка внедрения кода в метод PersonServiceImpl.create()
     * @return Новый Person.
     */
    @AfterReturning(value = "execution(* ru.clevertec.service.impl.PersonServiceImpl.create(..))", returning = "result" )
    public UUID afterCreate(JoinPoint joinPoint, Object result) {
        log.info("Вошли в аспект POST !");

        Person newPerson = personRepository.findByUuid((UUID) result)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        cache.put(newPerson.getUuid(), newPerson);
        log.info(cache.get(newPerson.getUuid()).toString());

        return newPerson.getUuid();
    }

    /**
     * Обновляет объект в БД и кеше.
     *
     * @param joinPoint Точка внедрения кода в метод PersonServiceImpl.update()
     */
    @AfterReturning(value = "execution(* ru.clevertec.service.impl.PersonServiceImpl.update(..))", returning = "result")
    public void aroundUpdate(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        RequestPersonDTO requestPersonDTO = (RequestPersonDTO) args[0];
        UUID uuid = (UUID) args[1];

        if (cache.get(uuid) != null) {

            Person person = cache.get(uuid);
            person.setName(requestPersonDTO.getName());
            person.setSurname(requestPersonDTO.getSurname());
            person.setSex(requestPersonDTO.getSex());
            person.setPassportSeries(requestPersonDTO.getPassportSeries());
            person.setPassportNumber(requestPersonDTO.getPassportNumber());
            person.setUpdateDate(LocalDateTime.now());

            log.info("Вошли в аспект PUT !");
            cache.put(person.getUuid(), person);
            log.info(cache.toString());
        }
    }

    /**
     * Удаление объекта из БД, а затем из кеша.
     *
     * @param joinPoint Точка внедрения кода в метод PersonServiceImpl.delete()
     */
    @AfterReturning(value = "execution(* ru.clevertec.service.impl.PersonServiceImpl.delete(..))")
    public void aroundDelete(JoinPoint joinPoint) {
        log.info("Вошли в аспект DELETE!");
        Object[] args = joinPoint.getArgs();
        UUID uuid = (UUID) args[0];
        cache.remove(uuid);
    }
}
