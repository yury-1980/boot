package ru.clevertec.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.service.HouseService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/houses")
public class HouseController {

    private HouseService services;

    @GetMapping
    public List<ResponseHouseDTO> getAllHouse(@RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "15") int pageSize) {
        return services.findByAll(pageNumber, pageSize);
    }

    @GetMapping("/{uuid}")
    public ResponseHouseDTO getHouse(@PathVariable("uuid") UUID uuid) throws Throwable {

        return services.findByUUID(uuid);
    }

    @PostMapping
    public UUID create(@RequestBody RequestHouseDTO requestHouseDTO) {

        return services.create(requestHouseDTO);
    }

    @PutMapping("/{uuid}")
    public void update(@RequestBody RequestHouseDTO requestHouseDTO, @PathVariable("uuid") UUID uuid) {
        services.update(requestHouseDTO, uuid);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        services.delete(uuid);
    }
}
