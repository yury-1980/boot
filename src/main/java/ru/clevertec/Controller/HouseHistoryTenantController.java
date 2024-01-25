package ru.clevertec.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.service.HouseHistoryTenantSrevice;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/persons")
public class HouseHistoryTenantController {

    private HouseHistoryTenantSrevice tenantSrevice;

    @GetMapping
    public ResponseEntity<List<ResponsePersonDTO>> findAllTenantsHouse(UUID houseUid) {
        return ResponseEntity.ok(tenantSrevice.findAllTenantsHouse(houseUid));
    }

    @GetMapping("/owners/{uuid}")
    public ResponseEntity<List<ResponsePersonDTO>> findAllOwnersHouse(@PathVariable("uuid") UUID houseUid) {
        return ResponseEntity.ok(tenantSrevice.findAllOwnersHouse(houseUid));
    }
}
