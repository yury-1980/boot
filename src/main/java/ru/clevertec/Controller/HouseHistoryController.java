package ru.clevertec.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.service.HouseHistorySrevice;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/houses")
public class HouseHistoryController {

    private HouseHistorySrevice houseSrevice;

    @GetMapping
    public ResponseEntity<List<ResponseHouseDTO>> findAllHousesTenant(UUID personUuid) {
        return ResponseEntity.ok(houseSrevice.findAllHousesTenant(personUuid));
    }

    @GetMapping("/owners/{uuid}")
    public ResponseEntity<List<ResponseHouseDTO>> findAllHousesOwner(@PathVariable("uuid") UUID personUuid) {
        return ResponseEntity.ok(houseSrevice.findAllHousesOwner(personUuid));
    }
}
