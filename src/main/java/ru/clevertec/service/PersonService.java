package ru.clevertec.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;

import java.util.UUID;

public interface PersonService extends Services<RequestPersonDTO, ResponsePersonDTO> {

    @Transactional
    void create(RequestPersonDTO requestPersonDTO, UUID uuid);
}
