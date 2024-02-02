package ru.clevertec.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.PostgreSQLContainerInitializer;
import ru.clevertec.entity.House;
import ru.clevertec.entity.type.PersonType;
import ru.clevertec.service.HouseService;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseHistoryRepositoryTest extends PostgreSQLContainerInitializer {

    private final HouseHistoryRepository houseHistoryRepository;
    private final HouseService houseService;

    @Test
    void shouldFindAllByHouseHistoriesPersonUuidAndHouseHistoriesTenant() {
        UUID uuidPerson = TestData.UUID_PERSON;
        PersonType personTypeTenant = PersonType.TENANT;

        List<House> houses = houseHistoryRepository.findAllByHouseHistoriesPersonUuidAndHouseHistoriesType(
                uuidPerson, personTypeTenant);

        int actual = houses.size();
        int expected = 1;

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllByHouseHistoriesPersonUuidAndHouseHistoriesOwner() {
        UUID uuidPerson = TestData.UUID_PERSON;
        PersonType personTypeOwner = PersonType.OWNER;

        List<House> houses = houseHistoryRepository.findAllByHouseHistoriesPersonUuidAndHouseHistoriesType(
                uuidPerson, personTypeOwner);

        int actual = houses.size();
        int expected = 2;

        assertEquals(expected, actual);
    }
}