package com.chat_application.Service;

import com.chat_application.entities.Message;
import com.chat_application.entities.Room;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface roomService {

    //create room
    Room createRoom(String roomId);
    //Join room
    Room joinRoom(String roomId);
    //get message
    List<Message> getMessage(String roomId,int page ,int size);
    //delete room
    void deleteRoom(String roomId);
    //delete message
    ResponseEntity<String> deleteMessage(String roomId, String messageId);
}
