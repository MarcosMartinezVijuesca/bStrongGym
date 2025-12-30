package com.gym.bstrong.exception;

public class ActivityNotFoundException extends Exception {
    public ActivityNotFoundException(String message) {
        super(message);
    }
    public ActivityNotFoundException() {
        super("Activity not found");
    }
}
