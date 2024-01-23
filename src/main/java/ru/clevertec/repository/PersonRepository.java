package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.entity.House;
import ru.clevertec.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @Query("SELECT h FROM House h JOIN h.ownersList o WHERE o.uuid = :uuid")
    List<House> findByUuidOwnerAndHousesList(@Param("uuid") UUID personUuid);
}
