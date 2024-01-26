package ru.clevertec.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.service.HouseService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/houses")
public class HouseController {

    private HouseService services;

    @GetMapping
    public ResponseEntity<List<ResponseHouseDTO>> getAllHouse(@RequestParam(defaultValue = "0") int pageNumber,
                                                              @RequestParam(defaultValue = "15") int pageSize) {
        return ResponseEntity.ok(services.findByAll(pageNumber, pageSize));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseHouseDTO> getHouse(@PathVariable("uuid") UUID uuid) throws Throwable {

        return ResponseEntity.ok(services.findByUUID(uuid));
    }

    @GetMapping("/persons/{uuid}")
    public ResponseEntity<List<ResponsePersonDTO>> getHousePersons(@PathVariable("uuid") UUID uuid) {

        return ResponseEntity.ok(services.getPersonsByHouse(uuid));
    }

    @PostMapping
    public ResponseEntity<UUID> createHouse(@RequestBody RequestHouseDTO requestHouseDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(services.create(requestHouseDTO));
    }

    @PostMapping("/owners")
    public ResponseEntity<Void> createHouseAndOwner(@RequestParam UUID house, @RequestParam UUID person) {
        services.addOwnerInHouse(house, person);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updateHouse(@RequestBody RequestHouseDTO requestHouseDTO,
                                            @PathVariable("uuid") UUID uuid) {
        services.update(requestHouseDTO, uuid);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PatchMapping
    public ResponseEntity<ResponseHouseDTO> updatePatcHouse(@RequestBody RequestHouseDTO requestHouseDTO,
                                                            @RequestParam UUID house, @RequestParam UUID person) {

        return ResponseEntity.ok(services.updatePatch(requestHouseDTO, house, person));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteHouse(@PathVariable("uuid") UUID uuid) {
        services.delete(uuid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
