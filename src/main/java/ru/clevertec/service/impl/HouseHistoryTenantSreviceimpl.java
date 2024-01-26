package ru.clevertec.service.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.repository.HouseHistoryTenantRepository;
import ru.clevertec.service.HouseHistoryTenantSrevice;

import java.util.List;
import java.util.UUID;

@Service
public class HouseHistoryTenantSreviceimpl implements HouseHistoryTenantSrevice {

    private HouseHistoryTenantRepository tenantRepository;

    /**
     * Поиск всех Person, когда-либо проживающих в доме.
     *
     * @param houseUid uuid house.
     * @return List<dto> Persons.
     */
    @Override
    public List<ResponsePersonDTO> findAllTenantsHouse(UUID houseUid) {
        return null;
    }

    /**
     * Поиск всех владельцев дома.
     *
     * @param houseUid uuid house.
     * @return List<dto> Persons.
     */
    @Override
    public List<ResponsePersonDTO> findAllOwnersHouse(UUID houseUid) {
        return null;
    }
}
