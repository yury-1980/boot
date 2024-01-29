package ru.clevertec.service;

import java.util.List;
import java.util.UUID;

public interface Services<T, U, E> {

    List<U> findByAll(int pageNumber, int pageSize);

    U findByUUID(UUID uuid);

    E update(T t, UUID uuid);

    U updatePatch(T t, UUID personUuid, UUID houseUuid);

    void delete(UUID uuid);
}
