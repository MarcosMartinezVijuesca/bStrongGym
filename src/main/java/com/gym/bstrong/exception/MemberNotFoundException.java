package com.gym.bstrong.exception;

public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(String message) {
        super(message);
    }
    public MemberNotFoundException() {
        super("Member not found");
    }
}
