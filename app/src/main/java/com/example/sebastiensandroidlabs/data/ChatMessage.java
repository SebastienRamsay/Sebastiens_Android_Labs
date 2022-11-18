package com.example.sebastiensandroidlabs.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {



    @PrimaryKey
    int  id;

    @ColumnInfo(name = "message")
    protected String message;
    @ColumnInfo(name="timeSent")
    protected String timeSent;
    @ColumnInfo(name="isSentButton")
    protected boolean sentOrRecieved;

    public void setChatMessage(int id, String message, String timeSent, boolean isSentButton){
        this.id = id;
        this.message = message;
        this.timeSent = timeSent;
        this.sentOrRecieved = isSentButton;
    }

    public int getId() {return id;}

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentOrRecieved() {
        return sentOrRecieved;
    }
}
