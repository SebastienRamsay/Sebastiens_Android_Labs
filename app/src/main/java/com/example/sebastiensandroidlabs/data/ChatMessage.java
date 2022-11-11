package com.example.sebastiensandroidlabs.data;
public class ChatMessage {

    String message;
    String timeSent;
    boolean isSentButton;

    public void setChatMessage(String message, String timeSent, boolean isSentButton){
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }
}
