package ru.clevertec.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;

import java.util.List;
import java.util.UUID;

public interface HouseService extends Services<RequestHouseDTO, ResponseHouseDTO> {

    @Transactional
    UUID create(RequestHouseDTO requestHouseDTO);

    @Transactional
    void createHouseAndOwner(UUID house, UUID person);

    List<ResponsePersonDTO> getPersonsByHouse(UUID houseUuid);
}
