package com.example.backend.exception;

public class DoctorAlreadyBookedException extends RuntimeException {

    public DoctorAlreadyBookedException(String message) {
        super(message);
    }
}
