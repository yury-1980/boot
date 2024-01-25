package ru.clevertec.service;

import ru.clevertec.dto.responseDTO.ResponsePersonDTO;

import java.util.List;
import java.util.UUID;

public interface HouseHistoryTenantSrevice {

    List<ResponsePersonDTO> findAllTenantsHouse(UUID houseUid);
    List<ResponsePersonDTO> findAllOwnersHouse(UUID houseUid);


}
