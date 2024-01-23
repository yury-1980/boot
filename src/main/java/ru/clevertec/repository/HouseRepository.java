package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.entity.House;
import ru.clevertec.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseRepository extends JpaRepository<House, UUID> {

    Optional<House> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @Query("SELECT p FROM Person p LEFT JOIN p.houseResident h WHERE h.uuid = :uuid")
    List<Person> findByUuidAndResidentsList(@Param("uuid") UUID houseUuid);
    //    @Query(nativeQuery = true, value = "SELECT h FROM house h LEFT JOIN person p on h.id = p.house_id WHERE h.uuid = :uuid")
}
