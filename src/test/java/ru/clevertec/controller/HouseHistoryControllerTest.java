package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.entity.type.PersonType;
import ru.clevertec.service.HouseHistorySrevice;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@WebMvcTest(HouseHistoryController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseHistoryControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    HouseHistorySrevice houseHistorySrevice;

    @Test
    @SneakyThrows
    void shoulFindAllHousesOwner() {
        UUID uuidPerson = TestData.UUID_PERSON;
        PersonType type = PersonType.OWNER;
        List<ResponseHouseDTO> responseHouseDTOsList = List.of(TestData.getResponseHouseDTO());

        when(houseHistorySrevice.findAllHousesTenantOrOwner(any(UUID.class), any(PersonType.class)))
                .thenReturn(responseHouseDTOsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/houses")
                                .param("personUuid", String.valueOf(uuidPerson))
                                .param("type", String.valueOf(type))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().is2xxSuccessful(), jsonPath("$", hasSize(responseHouseDTOsList.size())));
    }

    @Test
    @SneakyThrows
    void shouldFindAllHousesTenant() {
        UUID uuidPerson = TestData.UUID_PERSON;
        PersonType type = PersonType.TENANT;
        List<ResponseHouseDTO> responseHouseDTOsList = List.of(TestData.getResponseHouseDTO());

        when(houseHistorySrevice.findAllHousesTenantOrOwner(any(UUID.class), any(PersonType.class)))
                .thenReturn(responseHouseDTOsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/houses")
                                .param("personUuid", String.valueOf(uuidPerson))
                                .param("type", String.valueOf(type))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().is2xxSuccessful(), jsonPath("$", hasSize(responseHouseDTOsList.size())));
    }
}