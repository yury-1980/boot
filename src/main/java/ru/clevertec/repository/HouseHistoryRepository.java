package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.House;

import java.util.UUID;

@Repository
public interface HouseHistoryRepository extends JpaRepository<House, UUID> {


}
