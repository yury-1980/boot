package ru.clevertec.service;

import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;

import java.util.List;
import java.util.UUID;

public interface HouseService extends Services<RequestHouseDTO, ResponseHouseDTO, UUID> {

    UUID create(RequestHouseDTO requestHouseDTO);

    void addOwnerInHouse(UUID house, UUID person);

    List<ResponsePersonDTO> getPersonsByHouse(UUID houseUuid);
}
