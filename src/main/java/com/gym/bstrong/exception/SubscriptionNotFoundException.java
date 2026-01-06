package com.gym.bstrong.exception;

public class SubscriptionNotFoundException extends Exception {
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
    public SubscriptionNotFoundException() {
        super("Subscription not found");
    }
}
