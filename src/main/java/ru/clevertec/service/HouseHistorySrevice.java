package ru.clevertec.service;

import ru.clevertec.dto.responseDTO.ResponseHouseDTO;

import java.util.List;
import java.util.UUID;

public interface HouseHistorySrevice {

    List<ResponseHouseDTO> findAllHousesTenant(UUID personUuid);

    List<ResponseHouseDTO> findAllHousesOwner(UUID personUuid);
}
