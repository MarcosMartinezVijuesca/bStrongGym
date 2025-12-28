package com.gym.bstrong.exception;

public class MonitorNotFoundException extends Exception {
    public MonitorNotFoundException(String message) {
        super(message);
    }
    public MonitorNotFoundException() {
        super("Monitor not found");
    }
}
