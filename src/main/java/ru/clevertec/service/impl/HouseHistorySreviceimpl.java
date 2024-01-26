package ru.clevertec.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.mapper.HouseMapper;
import ru.clevertec.repository.HouseHistoryRepository;
import ru.clevertec.service.HouseHistorySrevice;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class HouseHistorySreviceimpl implements HouseHistorySrevice {

    private HouseHistoryRepository houseRepository;
    private HouseMapper houseMapper;

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
