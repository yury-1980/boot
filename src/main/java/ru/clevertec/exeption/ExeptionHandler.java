package ru.clevertec.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExeptionHandler {

    @ExceptionHandler
    public ResponseEntity<MessageResponse> hendlerExeption(EntityNotFoundExeption entityNotFoundExeption) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setInfo(entityNotFoundExeption.getMessage());

        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MessageResponse> hendlerExeption(Exception exception) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setInfo(exception.getMessage());

        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }


}
