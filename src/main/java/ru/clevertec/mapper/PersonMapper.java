package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    ResponsePersonDTO toResponsePersonDto(Person person);

    Person toPerson(RequestPersonDTO requestPersonDTO);
}
