package ru.clevertec.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.entity.House;
import ru.clevertec.entity.Person;
import ru.clevertec.exeption.EntityNotFoundExeption;
import ru.clevertec.mapper.HouseMapper;
import ru.clevertec.mapper.PersonMapper;
import ru.clevertec.repository.HouseRepository;
import ru.clevertec.repository.PersonRepository;
import ru.clevertec.service.PersonService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private HouseRepository houseRepository;
    private PersonRepository personRepository;
    private PersonMapper personMapper;
    private HouseMapper houseMapper;

    @Override
    public List<ResponsePersonDTO> findByAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return personRepository.findAll(pageRequest).stream()
                .map(person -> personMapper.toResponsePersonDto(person))
                .toList();
    }

    @Override
    public ResponsePersonDTO findByUUID(UUID uuid) {
        return personRepository.findByUuid(uuid)
                .map(person -> personMapper.toResponsePersonDto((Person) person))
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
    }

    @Override
    public List<ResponseHouseDTO> getHousesByOwner(UUID personUuid) {
        personRepository.findByUuid(personUuid)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));

        return houseRepository.findByUuidOwnerAndHousesList(personUuid).stream()
                .map(house -> houseMapper.toResponseHouseDTO(house))
                .toList();
    }

    @Override
    @Transactional
    public void create(RequestPersonDTO requestPersonDTO, UUID uuid) {
        houseRepository.findByUuid(uuid).ifPresent(house -> {
            Person person = personMapper.toPerson(requestPersonDTO);
            person.setUuid(UUID.randomUUID());
            person.setCreateDate(LocalDateTime.now());
            person.setUpdateDate(person.getCreateDate());
            person.setHouseResident(house);
            personRepository.save(person);
        });
    }

    @Override
    @Transactional
    public void update(RequestPersonDTO requestPersonDTO, UUID uuid) {
        personRepository.findByUuid(uuid).ifPresent(person -> {
            person.setName(requestPersonDTO.getName());
            person.setSurname(requestPersonDTO.getSurname());
            person.setSex(requestPersonDTO.getSex());
            person.setPassportSeries(requestPersonDTO.getPassportSeries());
            person.setPassportNumber(requestPersonDTO.getPassportNumber());
            person.setUpdateDate(LocalDateTime.now());
            personRepository.save(person);
        });
    }

    @Override
    @Transactional
    public ResponsePersonDTO updatePatch(RequestPersonDTO requestPersonDTO, UUID personUuid, UUID houseUuid) {

        Person oldPerson = personRepository.findByUuid(personUuid)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        Optional<House> house = houseRepository.findByUuid(houseUuid);

        if (requestPersonDTO.getName() != null) {
            oldPerson.setName(requestPersonDTO.getName());
            updateDate(oldPerson);
        }

        if (requestPersonDTO.getSurname() != null) {
            oldPerson.setSurname(requestPersonDTO.getSurname());
            updateDate(oldPerson);
        }

        if (requestPersonDTO.getSex() != null) {
            oldPerson.setSex(requestPersonDTO.getSex());
            updateDate(oldPerson);
        }

        if (requestPersonDTO.getPassportSeries() != null) {
            oldPerson.setPassportSeries(requestPersonDTO.getPassportSeries());
            updateDate(oldPerson);
        }

        if (requestPersonDTO.getPassportNumber() != null) {
            oldPerson.setPassportNumber(requestPersonDTO.getPassportNumber());
            updateDate(oldPerson);
        }

        house.ifPresent(oldPerson::setHouseResident);

        return personMapper.toResponsePersonDto(personRepository.save(oldPerson));
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        personRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class));
        personRepository.deleteByUuid(uuid);
    }

    private void updateDate(Person person) {
        person.setUpdateDate(LocalDateTime.now());
    }
}
