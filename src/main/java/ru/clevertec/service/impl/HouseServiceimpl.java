package ru.clevertec.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.entity.House;
import ru.clevertec.exeption.EntityNotFoundExeption;
import ru.clevertec.mapper.HouseMapper;
import ru.clevertec.repository.HouseRepository;
import ru.clevertec.service.HouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class HouseServiceimpl implements HouseService {

    private HouseRepository houseRepository;
    private HouseMapper mapper;

    @Override
    public List<ResponseHouseDTO> findByAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return houseRepository.findAll(pageRequest).stream()
                .map(house -> mapper.toResponseHouseDTO((House) house))
                .toList();
    }

    @Override
    public ResponseHouseDTO findByUUID(UUID uuid) throws Throwable {

        return houseRepository.findByUuid(uuid)
                .map(house -> mapper.toResponseHouseDTO((House) house))
                .orElseThrow(() -> new EntityNotFoundExeption("Object not found", UUID.class));

    }

    @Override
    @Transactional
    public UUID create(RequestHouseDTO requestHouseDTO) {
        House house = mapper.toHouse(requestHouseDTO);
        house.setUuid(UUID.randomUUID());
        house.setCreateDate(LocalDateTime.now());

        return houseRepository.save(house).getUuid();
    }

    @Override
    @Transactional
    public void update(RequestHouseDTO requestHouseDTO, UUID uuid) {
        houseRepository.findByUuid(uuid).ifPresent(house -> {
            house.setArea(requestHouseDTO.getArea());
            house.setCountry(requestHouseDTO.getCountry());
            house.setCity(requestHouseDTO.getCity());
            house.setStreet(requestHouseDTO.getStreet());
            house.setNumber(requestHouseDTO.getNumber());

            houseRepository.save(house);
        });
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        houseRepository.deleteByUuid(uuid);
    }
}
