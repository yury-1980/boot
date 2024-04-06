package ru.clevertec.util;

import ru.clevertec.dto.requestDTO.RequestHouseDTO;
import ru.clevertec.dto.requestDTO.RequestPersonDTO;
import ru.clevertec.dto.responseDTO.ResponseHouseDTO;
import ru.clevertec.dto.responseDTO.ResponsePersonDTO;
import ru.clevertec.entity.House;
import ru.clevertec.entity.Person;
import ru.clevertec.entity.type.Sex;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TestData {

    public static final UUID UUID_PERSON = java.util.UUID.fromString("d8b6eda1-2ac7-4190-8523-389b3cccffa9");
    public static final UUID UUID_HOUSE = java.util.UUID.fromString("99efee95-2f1c-459e-b97c-509f7399aa01");
    private static final String DATE_STRING = "2024-01-12 23:29:04.595";
    private static final String DATA_CREATE_HOUSE_STRING = "2024-01-12 23:29:04.595";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final LocalDateTime DATE_TIME = LocalDateTime.parse(DATE_STRING, FORMATTER);
    private static final LocalDateTime DATA_CREATE_HOUSE = LocalDateTime.parse(DATA_CREATE_HOUSE_STRING, FORMATTER);


    public static ResponsePersonDTO getResponsePersonDTO() {
        return ResponsePersonDTO.builder()
                .uuid(UUID_PERSON)
                .name("John")
                .surname("Doe")
                .sex(Sex.MALE)
                .passportSeries("HB")
                .passportNumber(3L)
                .createDate(DATE_TIME)
                .updateDate(DATE_TIME)
                .build();
    }

    public static Person getPerson() {
        return Person.builder()
                .uuid(UUID_PERSON)
                .name("John")
                .surname("Doe")
                .sex(Sex.MALE)
                .passportSeries("HB")
                .passportNumber(3L)
                .createDate(DATE_TIME)
                .updateDate(DATE_TIME)
                .build();
    }

    public static House getHouse() {
        return House.builder()
                .uuid(UUID_HOUSE)
                .area("Some area")
                .country("Some country")
                .city("Some city")
                .street("Some street")
                .number(123L)
                .createDate(DATA_CREATE_HOUSE)
                .build();
    }

    public static ResponseHouseDTO getResponseHouseDTO() {
        return ResponseHouseDTO.builder().build();
    }

    public static RequestPersonDTO getRequestPersonDTO() {
        return RequestPersonDTO.builder().build();
    }
    public static RequestHouseDTO getRequestHouseDTO() {
        return RequestHouseDTO.builder()
                .build();
    }
}
