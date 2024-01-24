package ru.clevertec.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;

import java.util.List;
import java.util.UUID;

public interface PersonService extends Services<RequestPersonDTO, ResponsePersonDTO> {

    @Transactional
    void create(RequestPersonDTO requestPersonDTO, UUID uuid);

    List<ResponseHouseDTO> getHousesByOwner(UUID personUuid);

//    ResponsePersonDTO updatePatch(RequestPersonDTO requestPersonDTO, UUID uuid);// TODO: 24-01-2024: удалить
}
