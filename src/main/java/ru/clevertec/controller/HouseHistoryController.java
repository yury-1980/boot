package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.entity.type.PersonType;
import ru.clevertec.service.HouseHistorySrevice;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/houses")
public class HouseHistoryController {

    private final HouseHistorySrevice houseSrevice;

    @GetMapping
    public ResponseEntity<List<ResponseHouseDTO>> findAllHousesTenant(@RequestParam("personUuid") UUID personUuid,
                                                                      @RequestParam("type") PersonType type) {
        return ResponseEntity.ok(houseSrevice.findAllHousesTenantOrOwner(personUuid, type));
    }
}
