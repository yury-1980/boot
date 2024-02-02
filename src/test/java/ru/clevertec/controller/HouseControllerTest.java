package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.service.HouseService;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@WebMvcTest(HouseController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class HouseControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private HouseService houseService;
    UUID uuidHouse;
    UUID uuidPerson;
    RequestHouseDTO requestHouseDTO;


    @BeforeEach
    void setUp() {
        uuidHouse = TestData.UUID_HOUSE;
        uuidPerson = TestData.UUID_PERSON;
        requestHouseDTO = TestData.getRequestHouseDTO();
    }

    @Test
    @SneakyThrows
    void shouldGetPageHouses() {
        int pageNumber = 0;
        int pageSize = 15;
        List<ResponseHouseDTO> responseHouseDTOsList = List.of(TestData.getResponseHouseDTO());

        when(houseService.findByAll(pageNumber, pageSize))
                .thenReturn(responseHouseDTOsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/houses")
                                .param("pageNumber", String.valueOf(pageNumber))
                                .param("pageSize", String.valueOf(pageSize))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(responseHouseDTOsList.size())));
    }

    @Test
    @SneakyThrows
    void shouldGetHouseByUuid() {
        ResponseHouseDTO responseHouseDTO = TestData.getResponseHouseDTO();

        when(houseService.findByUUID(any(UUID.class)))
                .thenReturn(responseHouseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/houses/{uuid}", uuidHouse)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().is2xxSuccessful(), jsonPath("$.uuid")
                        .value(responseHouseDTO.getUuid()
                                       .toString()));
    }

    @Test
    @SneakyThrows
    void shouldGetHousePersons() {
        List<ResponsePersonDTO> responsePersonDTOsList = List.of(TestData.getResponsePersonDTO());

        when(houseService.getPersonsByHouse(any(UUID.class)))
                .thenReturn(responsePersonDTOsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/houses/persons/{uuid}", uuidHouse)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(responsePersonDTOsList.size())));
    }

    @Test
    @SneakyThrows
    void shouldCreateHouse() {
        String jsonHouseDTO = TestData.convertToJson(requestHouseDTO);

        when(houseService.create(any(RequestHouseDTO.class)))
                .thenReturn(uuidHouse);

        mockMvc.perform(MockMvcRequestBuilders.post("/houses")
                                .content(jsonHouseDTO)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().is2xxSuccessful(), jsonPath("$")
                        .value(uuidHouse.toString()));
    }

    @Test
    @SneakyThrows
    void shouldAddOwnerInHouse() {

        mockMvc.perform(MockMvcRequestBuilders.post("/houses/owners")
                                .param("house", String.valueOf(uuidHouse))
                                .param("person", String.valueOf(uuidPerson))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());

        verify(houseService).addOwnerInHouse(uuidHouse, uuidPerson);
    }

    @Test
    @SneakyThrows
    void shouldUpdateHouseByUuid() {
        String jsonHouseDTO = TestData.convertToJson(requestHouseDTO);

        when(houseService.update(any(RequestHouseDTO.class), any(UUID.class)))
                .thenReturn(uuidHouse);

        mockMvc.perform(MockMvcRequestBuilders.put("/houses/{uuid}", uuidHouse)
                                .content(jsonHouseDTO)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value(uuidHouse.toString()));
    }

    @Test
    @SneakyThrows
    void shouldUpdatePatcHouse() {
        String jsonHouseDTO = TestData.convertToJson(requestHouseDTO);
        ResponseHouseDTO responseHouseDTO = TestData.getResponseHouseDTO();

        when(houseService.updatePatch(any(RequestHouseDTO.class), any(UUID.class), any(UUID.class)))
                .thenReturn(responseHouseDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/houses")
                                .content(jsonHouseDTO)
                                .param("house", String.valueOf(uuidHouse))
                                .param("person", String.valueOf(uuidPerson))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.uuid").value(responseHouseDTO.getUuid().toString()));
    }

    @Test
    @SneakyThrows
    void shouldDeleteHouseByUuid() {

        mockMvc.perform(MockMvcRequestBuilders.delete("/houses/{uud}", uuidHouse)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        verify(houseService).delete(uuidHouse);
    }
}