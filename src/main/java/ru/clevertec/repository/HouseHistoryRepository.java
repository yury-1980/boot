package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.House;
import ru.clevertec.entity.type.PersonType;

import java.util.List;
import java.util.UUID;

@Repository
public interface HouseHistoryRepository extends JpaRepository<House, UUID> {

    List<House> findAllByHouseHistoriesPersonUuidAndHouseHistoriesType(UUID personUuid, PersonType type);


}
