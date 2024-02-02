package ru.clevertec.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.PostgreSQLContainerInitializer;
import ru.clevertec.entity.Person;
import ru.clevertec.exeption.EntityNotFoundExeption;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PersonRepositoryTest extends PostgreSQLContainerInitializer {

    private final PersonRepository personRepository;

    @Test
    void findByUuid() {
        UUID expected = TestData.getPerson()
                .getUuid();
        UUID actual = personRepository.findByUuid(expected)
                .get()
                .getUuid();

        assertEquals(expected, actual);
    }

    @Test
    void deleteByUuid() {
        UUID uuidPerson = TestData.UUID_PERSON_TWO;
        personRepository.deleteByUuid(uuidPerson);

        assertThrows(EntityNotFoundExeption.class, () -> personRepository.findByUuid(uuidPerson)
                .orElseThrow(() -> EntityNotFoundExeption.of(UUID.class)));
    }

    @Test
    void findPersonByHouseResidentUuid() {
        String expected = "Masha";
        List<Person> personByHouseResidentUuid = personRepository.findPersonByHouseResidentUuid(TestData.UUID_HOUSE);
        String actual = personByHouseResidentUuid.get(0).getName();
        assertEquals(expected, actual);
    }
}