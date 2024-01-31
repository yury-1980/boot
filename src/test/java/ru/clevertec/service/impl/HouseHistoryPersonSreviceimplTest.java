package ru.clevertec.service.impl;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.PostgreSQLContainerInitializer;
import ru.clevertec.entity.Person;
import ru.clevertec.entity.type.PersonType;
import ru.clevertec.repository.HouseHistoryPersonRepository;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AllArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseHistoryPersonSreviceimplTest extends PostgreSQLContainerInitializer {

    @MockBean
    private HouseHistoryPersonRepository tenantRepository;

    @InjectMocks
    private HouseHistoryPersonSreviceimpl tenantSreviceimpl;

    @Test
    void findAllPersonsTenantsOrPersonsOwnersInHouse() {

        List<Person> personsList = List.of(TestData.getPerson());
        UUID uuidHouse = TestData.UUID_HOUSE;
        PersonType tenant = PersonType.TENANT;

        // given
        when(tenantRepository.findAllByPersonHistoriesHouseUuidAndPersonHistoriesType(any(UUID.class),
                                                                                      any(PersonType.class)))
                .thenReturn(personsList);

        // then
        tenantSreviceimpl.findAllPersonsTenantsOrPersonsOwnersInHouse(uuidHouse, tenant);

        // when
        verify(tenantRepository).findAllByPersonHistoriesHouseUuidAndPersonHistoriesType(uuidHouse, tenant);
    }
}