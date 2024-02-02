package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.entity.type.PersonType;
import ru.clevertec.service.HouseHistoryPersonSrevice;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/persons")
public class HouseHistoryPersonController {

    private final HouseHistoryPersonSrevice tenantSrevice;

    @GetMapping
    public ResponseEntity<List<ResponsePersonDTO>> findAllTenantsHouse(@RequestParam("houseUuid") UUID houseUid,
                                                                       @RequestParam("type") PersonType type) {
        return ResponseEntity.ok(tenantSrevice.findAllPersonsTenantsOrPersonsOwnersInHouse(houseUid, type));
    }
}
