package ru.clevertec.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.service.PersonService;

import java.util.List;
import java.util.UUID;

@RequestMapping("/persons")
@AllArgsConstructor
@RestController
public class PersonController {

    private PersonService services;

    @GetMapping
    public List<ResponsePersonDTO> getAllPerson(@RequestParam(defaultValue = "1") int pageNumber,
                                               @RequestParam(defaultValue = "15") int pageSize) {
        return services.findByAll(pageNumber, pageSize);
    }

    @GetMapping("/{uuid}")
    public ResponsePersonDTO getPerson(@PathVariable("uuid") UUID uuid) throws Throwable {

        return services.findByUUID(uuid);
    }

    @PostMapping("/{uuid}")
    public void addPerson(@RequestBody RequestPersonDTO requestPersonDTO, @PathVariable("uuid") UUID uuid) {

        services.create(requestPersonDTO, uuid);
    }

    @PutMapping("/{uuid}")
    public void update(@RequestBody RequestPersonDTO requestPersonDTO, @PathVariable("uuid") UUID uuid) {
        services.update(requestPersonDTO, uuid);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        services.delete(uuid);
    }
}
