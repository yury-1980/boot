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
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.entity.type.PersonType;
import ru.clevertec.service.HouseHistoryPersonSrevice;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@WebMvcTest(HouseHistoryPersonController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseHistoryPersonControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private HouseHistoryPersonSrevice houseHistoryPersonSrevice;

    @Test
    @SneakyThrows
    void shouldFindAllOwnersHouse() {
        UUID uuidHouse = TestData.UUID_HOUSE;
        PersonType type = PersonType.OWNER;
        List<ResponsePersonDTO> responsePersonDTOsList = List.of(TestData.getResponsePersonDTO());

        when(houseHistoryPersonSrevice.findAllPersonsTenantsOrPersonsOwnersInHouse(any(UUID.class), any(PersonType.class)))
                .thenReturn(responsePersonDTOsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/persons")
                                .param("houseUuid", String.valueOf(uuidHouse))
                                .param("type", String.valueOf(type))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().is2xxSuccessful(), jsonPath("$", hasSize(responsePersonDTOsList.size())));
    }

    @Test
    @SneakyThrows
    void shoulFindAllTenantsHouse() {
        UUID uuidHouse = TestData.UUID_HOUSE;
        PersonType type = PersonType.TENANT;
        List<ResponsePersonDTO> responsePersonDTOsList = List.of(TestData.getResponsePersonDTO());

        when(houseHistoryPersonSrevice.findAllPersonsTenantsOrPersonsOwnersInHouse(any(UUID.class), any(PersonType.class)))
                .thenReturn(responsePersonDTOsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/persons")
                                .param("houseUuid", String.valueOf(uuidHouse))
                                .param("type", String.valueOf(type))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().is2xxSuccessful(), jsonPath("$", hasSize(responsePersonDTOsList.size())));
    }
}