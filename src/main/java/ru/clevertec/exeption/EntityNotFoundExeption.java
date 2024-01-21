package ru.clevertec.exeption;

public class EntityNotFoundExeption extends RuntimeException{

    public EntityNotFoundExeption(String message, Class<?> clazz) {
        super(message + " " + clazz.getSimpleName());
    }
}
