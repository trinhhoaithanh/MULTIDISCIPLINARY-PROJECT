package com.example.androidiot;

public class MessageClass {
    public String message;
    public int sender;

    public MessageClass() {
    }

    public MessageClass(String message, int sender) {
        this.message = message;
        this.sender = sender;
    }
}
