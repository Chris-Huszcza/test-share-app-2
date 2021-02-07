package com.example.mytestapplication.data;

public class MissingPreferenceException extends RuntimeException {

    public MissingPreferenceException(String message) {
        super(message);
    }
}
