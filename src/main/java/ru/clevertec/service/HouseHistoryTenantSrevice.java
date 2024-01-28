package ru.clevertec.service;

import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.entity.type.PersonType;

import java.util.List;
import java.util.UUID;

public interface HouseHistoryTenantSrevice {

    List<ResponsePersonDTO> findAllPersonsTenantsOrPersonsOwnersInHouse(UUID houseUid, PersonType type);
}
