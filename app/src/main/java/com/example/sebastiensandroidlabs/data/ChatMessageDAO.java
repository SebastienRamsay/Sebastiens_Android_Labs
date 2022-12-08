package com.example.sebastiensandroidlabs.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDAO {

    @Insert
    public void instertMessage(ChatMessage message);

    @Query("SELECT * FROM ChatMessage")
    public List<ChatMessage> getAllMessages();

    @Delete
    void deleteMessage(ChatMessage message);

    @Query("DELETE FROM ChatMessage WHERE id > -1")
    void deleteAllMessages();
}
