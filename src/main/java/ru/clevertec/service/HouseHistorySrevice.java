package ru.clevertec.service;

import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.entity.type.PersonType;

import java.util.List;
import java.util.UUID;

public interface HouseHistorySrevice {

    List<ResponseHouseDTO> findAllHousesTenantOrOwner(UUID personUuid, PersonType type);
}
