package ru.clevertec.service.impl;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.PostgreSQLContainerInitializer;
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
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AllArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseServiceimplTest extends PostgreSQLContainerInitializer {

    @MockBean
    private HouseRepository houseRepository;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonMapper personMapper;

    @InjectMocks
    private HouseServiceimpl houseService;

    @MockBean
    private HouseMapper houseMapper;

    @Test
    void shouldReturnPage() {

        // given
        int pageNumber = 0;
        int pageSize = 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<ResponseHouseDTO> expected = List.of(TestData.getResponseHouseDTO());
        List<House> housesList = List.of(TestData.getHouse());
        Page<House> housePage = new PageImpl<>(housesList, pageRequest, housesList.size());

        when(houseRepository.findAll(pageRequest))
                .thenReturn(housePage);
        when(houseMapper.toResponseHouseDTO(any(House.class)))
                .thenReturn(expected.get(0));

        // when
        List<ResponseHouseDTO> actual = houseService.findByAll(pageNumber, pageSize);


        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldReturnHouseByUUID() {

        // given
        ResponseHouseDTO expected = TestData.getResponseHouseDTO();

        House house = TestData.getHouse();

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));
        when(houseMapper.toResponseHouseDTO(any(House.class)))
                .thenReturn(expected);

        // when
        ResponseHouseDTO actual = houseService.findByUUID(TestData.UUID_HOUSE);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetPersonsByHouse() {

        // given
        List<ResponsePersonDTO> expected = List.of(TestData.getResponsePersonDTO());
        List<Person> persons = List.of(TestData.getPerson());

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.ofNullable(TestData.getHouse()));
        when(personRepository.findPersonByHouseResidentUuid(any(UUID.class)))
                .thenReturn(persons);
        when(personMapper.toResponsePersonDto(any(Person.class)))
                .thenReturn(expected.get(0));

        // when
        List<ResponsePersonDTO> actual = houseService.getPersonsByHouse(TestData.UUID_HOUSE);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetEntityNotFoundExeptionWhenGetPersonsByHouse() {

        // given
        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundExeption.class, () -> houseService.getPersonsByHouse(TestData.UUID_HOUSE));
    }

    @Test
    void shouldCreateHouse() {

        // given
        UUID expected = TestData.getHouse()
                .getUuid();
        House house = TestData.getHouse();

        when(houseMapper.toHouse(any(RequestHouseDTO.class)))
                .thenReturn(house);
        when(houseRepository.save(any(House.class)))
                .thenAnswer(invocation -> {
                    House savedHouse = invocation.getArgument(0);
                    savedHouse.setUuid(expected);
                    return savedHouse;
                });

        // when
        UUID actual = houseService.create(TestData.getRequestHouseDTO());

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddOwnerInHouse() {

        // given
        House house = TestData.getHouse();
        UUID uuidHouse = TestData.UUID_HOUSE;
        Person person = TestData.getPerson();
        UUID uuidPerson = TestData.UUID_PERSON;

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));
        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));

        // when
        houseService.addOwnerInHouse(uuidHouse, uuidPerson);

        // then
        verify(houseRepository).save(house);
    }

    @Test
    void shouldReturnEntityNotFoundExeptionIfHouseNotFoundWhenAddOwner() {

        // given
        UUID uuidHouse = TestData.UUID_HOUSE;
        Person person = TestData.getPerson();
        UUID uuidPerson = TestData.UUID_PERSON;

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());
        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));

        // then
        assertThrows(EntityNotFoundExeption.class, () -> houseService.addOwnerInHouse(uuidHouse, uuidPerson));
    }

    @Test
    void shouldReturnEntityNotFoundExeptionIfPersonNotFoundWhenAddOwner() {

        // given
        House house = TestData.getHouse();
        UUID uuidHouse = TestData.UUID_HOUSE;
        UUID uuidPerson = TestData.UUID_PERSON;

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));
        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundExeption.class, () -> houseService.addOwnerInHouse(uuidHouse, uuidPerson));
    }

    @Test
    void shouldUpdateHouse() {

        // given
        House house = TestData.getHouse();
        UUID expected = TestData.UUID_HOUSE;

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));
        when(houseRepository.save(any(House.class)))
                .thenReturn(house);

        // when
        UUID actual = houseService.update(TestData.getRequestHouseDTO(), expected);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdatePatchHouse() {

        // given
        House house = TestData.getHouse();
        UUID uuidHouse = TestData.UUID_HOUSE;
        UUID uuidPerson = TestData.UUID_PERSON;
        Person person = TestData.getPerson();
        ResponseHouseDTO expected = TestData.getResponseHouseDTO();

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));
        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));
        when(houseRepository.save(any(House.class)))
                .thenReturn(house);
        when(houseMapper.toResponseHouseDTO(house))
                .thenReturn(expected);

        // when
        ResponseHouseDTO actual = houseService.updatePatch(TestData.getRequestHouseDTO(), uuidHouse,
                                                           uuidPerson);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEntityNotFoundExeptionWhenUpdatePatchHouseIfHouseNull() {

        // given
        UUID uuidHouse = TestData.UUID_HOUSE;
        UUID uuidPerson = TestData.UUID_PERSON;
        Person person = TestData.getPerson();

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());
        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(person));

        // then
        assertThrows(EntityNotFoundExeption.class,
                     () -> houseService.updatePatch(TestData.getRequestHouseDTO(), uuidHouse, uuidPerson));
    }

    @Test
    void shouldReturnEntityNotFoundExeptionWhenUpdatePatchHouseIfPersonNull() {

        // given
        House house = TestData.getHouse();
        UUID uuidHouse = TestData.UUID_HOUSE;
        UUID uuidPerson = TestData.UUID_PERSON;

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));
        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundExeption.class,
                     () -> houseService.updatePatch(TestData.getRequestHouseDTO(), uuidHouse, uuidPerson));
    }

    @Test
    void shouldDeleteHouse() {

        // given
        House house = TestData.getHouse();
        UUID uuidHouse = TestData.UUID_HOUSE;

        when(houseRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.of(house));

        // when
        houseService.delete(uuidHouse);

        // then
        verify(houseRepository)
                .deleteByUuid(uuidHouse);
    }

    @Test
    void shouldReturnEntityNotFoundExeptionWhenDeleteHouse() {

        // given
        UUID uuid = TestData.UUID_PERSON;

        when(personRepository.findByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundExeption.class, () -> houseService.delete(uuid));
    }
}