package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.entity.House;

@Mapper(componentModel = "spring")
public interface HouseMapper {

    ResponseHouseDTO toResponseHouseDTO(House house);

    House toHouse(RequestHouseDTO requestHouseDTO);
}
