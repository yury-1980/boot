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
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.service.PersonService;
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
@WebMvcTest(PersonController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PersonControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    UUID uuidHouse;
    UUID uuidPerson;
    RequestPersonDTO requestPersonDTO;
    ResponsePersonDTO responsePersonDTO;


    @BeforeEach
    void setUp() {
        uuidHouse = TestData.UUID_HOUSE;
        uuidPerson = TestData.UUID_PERSON;
        requestPersonDTO = TestData.getRequestPersonDTO();
        responsePersonDTO = TestData.getResponsePersonDTO();
    }

    @Test
    @SneakyThrows
    void shouldGetPagePersons() {
        int pageNumber = 1;
        int pageSize = 15;
        List<ResponsePersonDTO> responsePersonDTOsList = List.of(TestData.getResponsePersonDTO());

        when(personService.findByAll(pageNumber, pageSize))
                .thenReturn(responsePersonDTOsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/persons")
                                .param("pageNumber", String.valueOf(pageNumber))
                                .param("pageSize", String.valueOf(pageSize))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(responsePersonDTOsList.size())));
    }

    @Test
    @SneakyThrows
    void shouldGetPersonByUuid() {

        when(personService.findByUUID(any(UUID.class)))
                .thenReturn(responsePersonDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/persons/{uuid}", uuidPerson)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().is2xxSuccessful(), jsonPath("$.uuid")
                        .value(responsePersonDTO.getUuid()
                                       .toString()));
    }

    @Test
    @SneakyThrows
    void shouldGetHousesByOwner() {
        List<ResponseHouseDTO> responseHouseDTOsList = List.of(TestData.getResponseHouseDTO());

        when(personService.getHousesByOwner(any(UUID.class)))
                .thenReturn(responseHouseDTOsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/persons/houses/{uuid}", uuidPerson)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(responseHouseDTOsList.size())));
    }

    @Test
    @SneakyThrows
    void shouldCreatePerson() {
        String jsonPersonDTO = TestData.convertToJson(requestPersonDTO);

        when(personService.create(any(RequestPersonDTO.class), any(UUID.class)))
                .thenReturn(uuidPerson);

        mockMvc.perform(MockMvcRequestBuilders.post("/persons/{uuid}", uuidHouse)
                                .content(jsonPersonDTO)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().is2xxSuccessful(), jsonPath("$")
                        .value(uuidPerson.toString()));
    }

    @Test
    @SneakyThrows
    void shouldUpdatePerson() {
        String jsonPersonDTO = TestData.convertToJson(requestPersonDTO);

        when(personService.update(any(RequestPersonDTO.class), any(UUID.class)))
                .thenReturn(uuidPerson);

        mockMvc.perform(MockMvcRequestBuilders.put("/persons/{uuid}", uuidPerson)
                                .content(jsonPersonDTO)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value(uuidPerson.toString()));
    }

    @Test
    @SneakyThrows
    void shouldUpdatePatchPerson() {
        String jsonPersonDTO = TestData.convertToJson(requestPersonDTO);

        when(personService.updatePatch(any(RequestPersonDTO.class), any(UUID.class), any(UUID.class)))
                .thenReturn(responsePersonDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/persons")
                                .content(jsonPersonDTO)
                                .param("person", String.valueOf(uuidPerson))
                                .param("house", String.valueOf(uuidHouse))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.uuid").value(responsePersonDTO.getUuid()
                                                            .toString()));
    }

    @Test
    @SneakyThrows
    void shouldDeletePersonByUuid() {

        mockMvc.perform(MockMvcRequestBuilders.delete("/persons/{uud}", uuidPerson)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        verify(personService).delete(uuidPerson);
    }
}