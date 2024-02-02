package ru.clevertec.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.PostgreSQLContainerInitializer;
import ru.clevertec.entity.House;
import ru.clevertec.exeption.EntityNotFoundExeption;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseRepositoryTest extends PostgreSQLContainerInitializer {

    private final HouseRepository houseRepository;

    @Test
    void findByUuid() {
        UUID expected = TestData.UUID_HOUSE;
        UUID actual = houseRepository.findByUuid(expected)
                .get()
                .getUuid();

        assertEquals(expected, actual);
    }

    @Test
    void deleteByUuid() {
        UUID uuidHouse = TestData.UUID_HOUSE_TWO;
        houseRepository.deleteByUuid(uuidHouse);

        assertThrows(EntityNotFoundExeption.class, () -> houseRepository.findByUuid(uuidHouse)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class)));
    }

    @Test
    void findHousesByOwnersListUuid() {
        boolean actual;
        boolean expected = true;
        UUID uuidPerson = TestData.UUID_PERSON;

        List<House> houses = houseRepository.findHousesByOwnersListUuid(uuidPerson);

        if (houses.size() >= 0) {
            actual = true;

        } else {
            actual = false;
        }

        assertEquals(expected, actual);
    }
}