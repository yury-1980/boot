package ru.clevertec.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.entity.House;
import ru.clevertec.entity.Person;
import ru.clevertec.exeption.EntityAlreadyExists;
import ru.clevertec.exeption.EntityNotFoundExeption;
import ru.clevertec.mapper.HouseMapper;
import ru.clevertec.mapper.PersonMapper;
import ru.clevertec.repository.HouseRepository;
import ru.clevertec.repository.PersonRepository;
import ru.clevertec.service.HouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class HouseServiceimpl implements HouseService {

    private HouseRepository houseRepository;
    private PersonRepository personRepository;
    private HouseMapper houseMapper;
    private PersonMapper personMapper;

    /**
     * Выбор всех House из заданной страницы.
     *
     * @param pageNumber Номер страницы.
     * @param pageSize   Размер страницы.
     * @return List<ResponseHouseDTO>  Houses, список.
     */
    @Override
    public List<ResponseHouseDTO> findByAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return houseRepository.findAll(pageRequest).stream()
                .map(house -> houseMapper.toResponseHouseDTO(house))
                .toList();
    }

    /**
     * Выбор заданного дома, по его uuid.
     *
     * @param uuid UUID.
     * @return ResponseHouseDTO.
     */
    @Override
    public ResponseHouseDTO findByUUID(UUID uuid) {

        return houseRepository.findByUuid(uuid)
                .map(house -> houseMapper.toResponseHouseDTO((House) house))
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));

    }

    /**
     * Выбирает всех жильцов, по uuid House.
     *
     * @param houseUuid uuid дома(House).
     * @return List<dto> жильцов (residents).
     */
    @Override
    public List<ResponsePersonDTO> getPersonsByHouse(UUID houseUuid) {
        houseRepository.findByUuid(houseUuid)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        return personRepository.findPersonByHouseResidentUuid(houseUuid).stream()
                .map(person -> personMapper.toResponsePersonDto(person))
                .toList();
    }

    /**
     * Создание дома.
     *
     * @param requestHouseDTO RequestHouseDTO.
     * @return UUID созданного дома.
     */
    @Override
    @Transactional
    public UUID create(RequestHouseDTO requestHouseDTO) {
        House house = houseMapper.toHouse(requestHouseDTO);
        house.setUuid(UUID.randomUUID());
        house.setCreateDate(LocalDateTime.now());

        return houseRepository.save(house).getUuid();
    }

    /**
     * Добавление владельца (Owner) дома.
     *
     * @param house  UUID House.
     * @param person UUID Person.
     */
    @Override
    @Transactional
    public void addOwnerInHouse(UUID house, UUID person) {
        House houseOwner = houseRepository.findByUuid(house)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        Person owner = personRepository.findByUuid(person)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        houseOwner.getOwnersList().add(owner);
        houseRepository.save(houseOwner);

    }

    /**
     * Обновление дома целеком.
     *
     * @param requestHouseDTO Обновлённый RequestHouseDTO.
     * @param uuid            UUID дома.
     */
    @Override
    @Transactional
    public UUID update(RequestHouseDTO requestHouseDTO, UUID uuid) {
        AtomicReference<House> newHouse = new AtomicReference<>();

        houseRepository.findByUuid(uuid).ifPresent(house -> {
            house.setArea(requestHouseDTO.getArea());
            house.setCountry(requestHouseDTO.getCountry());
            house.setCity(requestHouseDTO.getCity());
            house.setStreet(requestHouseDTO.getStreet());
            house.setNumber(requestHouseDTO.getNumber());

            newHouse.set(houseRepository.save(house));
        });

        return newHouse.get().getUuid();
    }

    /**
     * Метод изменяет все параметры House
     *
     * @param requestHouseDTO тело запроса на изменение характеристик House.
     * @param houseUUID       UUID дома в БД.
     * @param personUUID      UUID Person который будет владельцем дома (Owner).
     * @return DTO дома.
     */
    @Override
    @Transactional
    public ResponseHouseDTO updatePatch(RequestHouseDTO requestHouseDTO, UUID houseUUID, UUID personUUID) {
        House oldHouse = houseRepository.findByUuid(houseUUID)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));

        Person person = personRepository.findByUuid(personUUID)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));

        if (requestHouseDTO.getArea() != null) {
            oldHouse.setArea(requestHouseDTO.getArea());
        }

        if (requestHouseDTO.getCountry() != null) {
            oldHouse.setCountry(requestHouseDTO.getCountry());
        }

        if (requestHouseDTO.getCity() != null) {
            oldHouse.setCity(requestHouseDTO.getCity());
        }

        if (requestHouseDTO.getStreet() != null) {
            oldHouse.setStreet(requestHouseDTO.getStreet());
        }

        if (requestHouseDTO.getNumber() != null) {
            oldHouse.setNumber(requestHouseDTO.getNumber());
        }

        if (oldHouse.getOwnersList().contains(person)) {
            throw EntityAlreadyExists.of(person.getClass());

        } else {
            oldHouse.getOwnersList().add(person);
        }

        return houseMapper.toResponseHouseDTO(houseRepository.save(oldHouse));
    }

    /**
     * Удаление дома по его UUID.
     *
     * @param uuid UUID.
     */
    @Override
    @Transactional
    public void delete(UUID uuid) {
        houseRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        houseRepository.deleteByUuid(uuid);
    }
}
