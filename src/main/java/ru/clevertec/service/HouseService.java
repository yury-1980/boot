package ru.clevertec.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;

import java.util.UUID;

public interface HouseService extends Services<RequestHouseDTO, ResponseHouseDTO> {

    @Transactional
    UUID create(RequestHouseDTO requestHouseDTO);
}
