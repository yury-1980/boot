package ru.clevertec.service.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.repository.HouseHistoryRepository;
import ru.clevertec.service.HouseHistorySrevice;

import java.util.List;
import java.util.UUID;

@Service
public class HouseHistorySreviceimpl implements HouseHistorySrevice {

    private HouseHistoryRepository houseRepository;

    /**
     * Поиск всех домов, где проживал Person.
     *
     * @param personUuid uuid person.
     * @return List<dto> домов.
     */
    @Override
    public List<ResponseHouseDTO> findAllHousesTenant(UUID personUuid) {
        return null;
    }

    /**
     * Поиск всех домов, которыми владел Person.
     *
     * @param personUuid uuid person.
     * @return List<dto> домов.
     */
    @Override
    public List<ResponseHouseDTO> findAllHousesOwner(UUID personUuid) {
        return null;
    }
}
