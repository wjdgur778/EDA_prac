package com.example.demo;


public class UserCreatedEvent {
    private String userEmail;

    public UserCreatedEvent(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
