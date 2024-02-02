package ru.clevertec.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.PostgreSQLContainerInitializer;
import ru.clevertec.entity.Person;
import ru.clevertec.entity.type.PersonType;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseHistoryPersonRepositoryTest extends PostgreSQLContainerInitializer {

    private final HouseHistoryPersonRepository historyPersonRepository;

    @Test
    void shouldFindAllByPersonHistoriesHouseUuidAndPersonHistoriesTenant() {
        UUID uuidHouse = TestData.UUID_HOUSE;
        PersonType personTypeTenant = PersonType.TENANT;

        List<Person> persons = historyPersonRepository.findAllByPersonHistoriesHouseUuidAndPersonHistoriesType(
                uuidHouse, personTypeTenant);

        int actual = persons.size();
        int expected = 2;

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllByPersonHistoriesHouseUuidAndPersonHistoriesOwner() {
        UUID uuidHouse = TestData.UUID_HOUSE;
        PersonType personTypeOwner = PersonType.OWNER;

        List<Person> persons = historyPersonRepository.findAllByPersonHistoriesHouseUuidAndPersonHistoriesType(
                uuidHouse, personTypeOwner);

        int actual = persons.size();
        int expected = 1;

        assertEquals(expected, actual);
    }
}