package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.PostgreSQLContainerInitializer;
import ru.clevertec.entity.House;
import ru.clevertec.entity.type.PersonType;
import ru.clevertec.repository.HouseHistoryRepository;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseHistorySreviceimplTest extends PostgreSQLContainerInitializer {

    @MockBean
    private final HouseHistoryRepository historyRepository;

    @InjectMocks
    private final HouseHistorySreviceimpl historySrevice;

    @Test
    void findAllHousesTenantOrOwner() {

        // given
        List<House> housesList = List.of(TestData.getHouse());
        UUID uuidHouse = TestData.UUID_HOUSE;
        PersonType tenant = PersonType.TENANT;

        when(historyRepository.findAllByHouseHistoriesPersonUuidAndHouseHistoriesType(any(UUID.class),
                                                                                      any(PersonType.class)))
                .thenReturn(housesList);

        // then
        historySrevice.findAllHousesTenantOrOwner(uuidHouse, tenant);

        // when
        verify(historyRepository).findAllByHouseHistoriesPersonUuidAndHouseHistoriesType(uuidHouse, tenant);
    }
}