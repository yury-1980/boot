package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.Person;
import ru.clevertec.entity.type.PersonType;

import java.util.List;
import java.util.UUID;

@Repository
public interface HouseHistoryPersonRepository extends JpaRepository<Person, UUID> {

    List<Person> findAllByPersonHistoriesHouseUuidAndPersonHistoriesType(UUID houseUuid, PersonType personType);
}
