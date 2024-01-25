package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.House;

import java.util.List;
import java.util.UUID;

@Repository
public interface HouseHistoryRepository extends JpaRepository<House, UUID> {// TODO: 25-01-2024: было <HouseHistory, UUID>

    List<House> findHousesBy (UUID personUuid);
}
