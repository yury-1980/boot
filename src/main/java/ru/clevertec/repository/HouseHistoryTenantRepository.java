package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.HouseHistory;

import java.util.UUID;

@Repository
public interface HouseHistoryTenantRepository extends JpaRepository<HouseHistory, UUID> {
}
