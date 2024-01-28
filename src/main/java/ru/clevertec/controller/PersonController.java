package ru.clevertec.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.service.PersonService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private PersonService services;

    @GetMapping
    public ResponseEntity<List<ResponsePersonDTO>> getAllPerson(@RequestParam(defaultValue = "1") int pageNumber,
                                                                @RequestParam(defaultValue = "15") int pageSize) {
        return ResponseEntity.ok(services.findByAll(pageNumber, pageSize));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponsePersonDTO> getPerson(@PathVariable("uuid") UUID uuid) throws Throwable {

        return ResponseEntity.ok(services.findByUUID(uuid));
    }

    @GetMapping("/houses/{uuid}")
    public ResponseEntity<List<ResponseHouseDTO>> getHousesByOwner(@PathVariable("uuid") UUID personUuid) {

        return ResponseEntity.ok(services.getHousesByOwner(personUuid));
    }

    @PostMapping("/{uuid}")
    public ResponseEntity<Void> createPerson(@RequestBody RequestPersonDTO requestPersonDTO,
                                             @PathVariable("uuid") UUID uuid) {
        services.create(requestPersonDTO, uuid);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updatePerson(@RequestBody RequestPersonDTO requestPersonDTO,
                                             @PathVariable("uuid") UUID uuid) {
        services.update(requestPersonDTO, uuid);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PatchMapping
    public ResponseEntity<ResponsePersonDTO> updatePatchPerson(@RequestBody RequestPersonDTO requestPersonDTO,
                                                               @RequestParam UUID person, @RequestParam UUID house) {

        return ResponseEntity.ok(services.updatePatch(requestPersonDTO, person, house));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePerson(@PathVariable("uuid") UUID uuid) {
        services.delete(uuid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
