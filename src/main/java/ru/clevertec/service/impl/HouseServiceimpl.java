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
import ru.clevertec.exeption.EntityNotFoundExeption;
import ru.clevertec.mapper.HouseMapper;
import ru.clevertec.mapper.PersonMapper;
import ru.clevertec.repository.HouseRepository;
import ru.clevertec.repository.PersonRepository;
import ru.clevertec.service.HouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class HouseServiceimpl implements HouseService {

    private HouseRepository houseRepository;
    private PersonRepository personRepository;
    private HouseMapper houseMapper;
    private PersonMapper personMapper;

    @Override
    public List<ResponseHouseDTO> findByAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return houseRepository.findAll(pageRequest).stream()
                .map(house -> houseMapper.toResponseHouseDTO(house))
                .toList();
    }

    @Override
    public ResponseHouseDTO findByUUID(UUID uuid) {

        return houseRepository.findByUuid(uuid)
                .map(house -> houseMapper.toResponseHouseDTO((House) house))
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));

    }

    @Override
    public List<ResponsePersonDTO> getPersonsByHouse(UUID houseUuid) {
        houseRepository.findByUuid(houseUuid)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        return personRepository.findByUuidAndResidentsList(houseUuid).stream()
                .map(person -> personMapper.toResponsePersonDto(person))
                .toList();
    }

    @Override
    @Transactional
    public UUID create(RequestHouseDTO requestHouseDTO) {
        House house = houseMapper.toHouse(requestHouseDTO);
        house.setUuid(UUID.randomUUID());
        house.setCreateDate(LocalDateTime.now());

        return houseRepository.save(house).getUuid();
    }

    @Override
    public void createHouseAndOwner(UUID house, UUID person) {
        House houseOwner = houseRepository.findByUuid(house)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        Person owner = personRepository.findByUuid(person)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        houseOwner.getOwnersList().add(owner);
        houseRepository.save(houseOwner);

    }

    @Override
    @Transactional
    public void update(RequestHouseDTO requestHouseDTO, UUID uuid) {
        houseRepository.findByUuid(uuid).ifPresent(house -> {
            house.setArea(requestHouseDTO.getArea());
            house.setCountry(requestHouseDTO.getCountry());
            house.setCity(requestHouseDTO.getCity());
            house.setStreet(requestHouseDTO.getStreet());
            house.setNumber(requestHouseDTO.getNumber());

            houseRepository.save(house);
        });
    }

    @Override
    @Transactional
    public ResponseHouseDTO updatePatch(RequestHouseDTO requestHouseDTO, UUID uuid) {
        House oldHouse = houseRepository.findByUuid(uuid)
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

        return houseMapper.toResponseHouseDTO(houseRepository.save(oldHouse));
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        houseRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        houseRepository.deleteByUuid(uuid);
    }
}
