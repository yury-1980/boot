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
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.entity.House;
import ru.clevertec.exeption.EntityNotFoundExeption;
import ru.clevertec.mapper.HouseMapper;
import ru.clevertec.repository.HouseRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class CacheHouseAspect {

    private LRUCache<UUID, House> cache;
    private HouseRepository houseRepository;
    private HouseMapper houseMapper;

    @PostConstruct
    void init() {
        log.info(cache.toString());
    }

    /**
     * Возвращает объект из кеша, если нет, то читает из базы.
     *
     * @param joinPoint Точка внедрения кода в метод HouseServiceimpl.findByUUID()
     * @return ResponseHouseDTO.
     * @throws EntityNotFoundExeption если не найден.
     */
    @Around("execution(* ru.clevertec.service.impl.HouseServiceimpl.findByUUID(..))")
    public ResponseHouseDTO aroundfindByUUID(ProceedingJoinPoint joinPoint) {
        log.info("Вошли в аспект GET !");
        Object[] args = joinPoint.getArgs();
        UUID uuid = (UUID) args[0];
        House house = cache.get(uuid);

        if (house != null) {
            log.info("Объект в кеше найден!");
            return houseMapper.toResponseHouseDTO(house);

        } else {
            log.info("Объект взят из базы !");
            return houseRepository.findByUuid(uuid)
                    .stream()
                    .peek(house1 -> cache.put(house1.getUuid(), house1))
                    .map(house1 -> houseMapper.toResponseHouseDTO(house1))
                    .findFirst()
                    .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        }
    }

    /**
     * Создаёт объект House из объекта RequestHouseDTO. И помещает в БД, а затем в кеш.
     *
     * @param joinPoint Точка внедрения кода в метод HouseServiceimpl.create()
     * @return Новый House.
     */
    @AfterReturning(value = "execution(* ru.clevertec.service.impl.HouseServiceimpl.create(..))", returning = "result")
    public UUID afterCreate(JoinPoint joinPoint, Object result) {
        log.info("Вошли в аспект POST !");
        Object[] args = joinPoint.getArgs();
        House house = houseMapper.toHouse((RequestHouseDTO) args[0]);
        house.setUuid((UUID) result);
        house.setCreateDate(LocalDateTime.now());

        cache.put(house.getUuid(), house);
        log.info(cache.get(house.getUuid())
                         .toString());

        return house.getUuid();
    }

    /**
     * Обновляет объект в БД и кеше.
     *
     * @param joinPoint Точка внедрения кода в метод HouseServiceimpl.update()
     */
    @AfterReturning(value = "execution(* ru.clevertec.service.impl.HouseServiceimpl.update(..))", returning = "result")
    public void aroundUpdate(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        RequestHouseDTO requestHouseDTO = (RequestHouseDTO) args[0];
        UUID uuid = (UUID) args[1];

        if (cache.get(uuid) != null) {

            House house = cache.get(uuid);
            house.setArea(requestHouseDTO.getArea());
            house.setCountry(requestHouseDTO.getCountry());
            house.setCity(requestHouseDTO.getCity());
            house.setStreet(requestHouseDTO.getStreet());
            house.setNumber(requestHouseDTO.getNumber());

            log.info("Вошли в аспект PUT !");
            cache.put(house.getUuid(), house);
            log.info(cache.toString());
        }
    }

    /**
     * Удаление объекта из БД, а затем из кеша.
     *
     * @param joinPoint Точка внедрения кода в метод HouseServiceimpl.delete()
     */
    @AfterReturning(value = "execution(* ru.clevertec.service.impl.HouseServiceimpl.delete(..))")
    public void aroundDelete(JoinPoint joinPoint) {
        log.info("Вошли в аспект DELETE!");
        Object[] args = joinPoint.getArgs();
        UUID uuid = (UUID) args[0];
        cache.remove(uuid);
    }
}
