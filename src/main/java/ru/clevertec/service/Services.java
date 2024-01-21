package ru.clevertec.service;

import java.util.List;
import java.util.UUID;

public interface Services<T, U> {

    List<U> findByAll(int pageNumber, int pageSize);

    U findByUUID(UUID uuid) throws Throwable;

    void update(T t, UUID uuid);

    void delete(UUID uuid);
}
