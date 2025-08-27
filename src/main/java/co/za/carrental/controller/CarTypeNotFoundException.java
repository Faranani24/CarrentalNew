package co.za.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarTypeNotFoundException extends RuntimeException {
    public CarTypeNotFoundException(String id) {
        super("CarType not found: " + id);
    }
}