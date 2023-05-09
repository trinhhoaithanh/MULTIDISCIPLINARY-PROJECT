package com.example.androidiot;

public class BotResponse {
    private String recipient;
    private String text;

    public BotResponse(String recipient, String text) {
        this.recipient = recipient;
        this.text = text;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
