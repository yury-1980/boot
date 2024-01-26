package ru.clevertec.service;

import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;

import java.util.List;
import java.util.UUID;

public interface PersonService extends Services<RequestPersonDTO, ResponsePersonDTO> {

    void create(RequestPersonDTO requestPersonDTO, UUID uuid);

    List<ResponseHouseDTO> getHousesByOwner(UUID personUuid);
}
