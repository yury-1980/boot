package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.PostgreSQLContainerInitializer;
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
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PersonServiceImplTest extends PostgreSQLContainerInitializer {

    @MockBean
    private final HouseRepository houseRepository;

    @MockBean
    private final PersonRepository personRepository;

    @MockBean
    private final PersonMapper personMapper;

    @InjectMocks
    private final PersonServiceImpl personService;

    @MockBean
    private final HouseMapper houseMapper;

    @Test
    void shouldFindByPage() {

        // given
        int pageNumber = 0;
        int pageSize = 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<ResponsePersonDTO> expected = List.of(TestData.getResponsePersonDTO());
        List<Person> personsList = List.of(TestData.getPerson());
        Page<Person> personPage = new PageImpl<>(personsList, pageRequest, personsList.size());

        when(personRepository.findAll(pageRequest))
                .thenReturn(personPage);
        when(personMapper.toResponsePersonDto(any(Person.class)))
                .thenReturn(expected.get(0));

        // when
        List<ResponsePersonDTO> actual = personService.findByAll(pageNumber, pageSize);


        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindPersonByUUID() {

        // given
        ResponsePersonDTO expected = TestData.getResponsePersonDTO();

        Person person = TestData.getPerson();

        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));
        when(personMapper.toResponsePersonDto(any(Person.class)))
                .thenReturn(expected);

        // when
        ResponsePersonDTO actual = personService.findByUUID(TestData.UUID_PERSON);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetHousesByOwner() {

        // given
        List<ResponseHouseDTO> expected = List.of(TestData.getResponseHouseDTO());
        List<House> houses = List.of(TestData.getHouse());

        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.ofNullable(TestData.getPerson()));
        when(houseRepository.findHousesByOwnersListUuid(any(UUID.class)))
                .thenReturn(houses);
        when(houseMapper.toResponseHouseDTO(any(House.class)))
                .thenReturn(expected.get(0));

        // when
        List<ResponseHouseDTO> actual = personService.getHousesByOwner(TestData.UUID_PERSON);


        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEntityNotFoundExeptionFromGetHousesByOwner() {

        // given
        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundExeption.class,
                                () -> personService.getHousesByOwner(TestData.UUID_PERSON));
    }

    @Test
    void shouldCreatePerson() {

        // given
        UUID expected = TestData.getPerson()
                .getUuid();
        Person person = TestData.getPerson();
        House house = TestData.getHouse();

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));
        when(personMapper.toPerson(any(RequestPersonDTO.class)))
                .thenReturn(person);
        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class)))
                .thenAnswer(invocation -> {
            Person savedPerson = invocation.getArgument(0);
            savedPerson.setUuid(expected);
            return savedPerson;
        });

        // when
        UUID actual = personService.create(TestData.getRequestPersonDTO(), TestData.UUID_HOUSE);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEntityNotFoundExeptionFromCreateIfHouseNull() {

        // given
        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundExeption.class,
                                () -> personService.create(TestData.getRequestPersonDTO(), TestData.UUID_HOUSE));
    }

    @Test
    void shouldUpdatePerson() {

        // given
        UUID expected = TestData.getPerson()
                .getUuid();
        Person person = TestData.getPerson();
        RequestPersonDTO requestPersonDTO = TestData.getRequestPersonDTO();

        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class)))
                .thenReturn(person);

        // when
        UUID actual = personService.update(requestPersonDTO, expected);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdatePatchPerson() {

        // given
        ResponsePersonDTO expected = TestData.getResponsePersonDTO();
        Person person = TestData.getPerson();
        House house = TestData.getHouse();

        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));
        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));
        when(personRepository.save(any(Person.class)))
                .thenReturn(person);
        when(personMapper.toResponsePersonDto(person))
                .thenReturn(expected);

        // when
        ResponsePersonDTO actual = personService.updatePatch(TestData.getRequestPersonDTO(), TestData.UUID_PERSON,
                                                             TestData.UUID_HOUSE);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldDeletePersonByUUID() {

        // given
        Person person = TestData.getPerson();
        UUID uuid = TestData.UUID_PERSON;

        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));

        // when
       personService.delete(uuid);

        // then
        Mockito.verify(personRepository).deleteByUuid(uuid);
    }

    @Test
    void shouldReturnEntityNotFoundExeptionIfPersonNull() {

        // given
        UUID uuid = TestData.UUID_PERSON;

        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundExeption.class, () -> personService.delete(uuid));
    }
}