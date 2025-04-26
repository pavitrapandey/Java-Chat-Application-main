package com.chat_application.Service.impl;

import com.chat_application.Service.roomService;
import com.chat_application.entities.Message;
import com.chat_application.entities.Room;
import com.chat_application.repositories.roomRepo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements roomService {

    @Autowired
    private roomRepo roomRepo;

    Logger logger= org.slf4j.LoggerFactory.getLogger(RoomServiceImpl.class);

    @Override
    public Room createRoom(String roomId) {
        if(roomRepo.findByRoomId(roomId)!= null){
            throw new RuntimeException("Room already exists");
        }
        //create new room
        Room room = new Room();
        room.setRoomId(roomId); // Corrected method name
        roomRepo.save(room);

        logger.info("Room created with ID: "+roomId);
        return room;
    }

    @Override
    public Room joinRoom(String roomId){
        Room room=roomRepo.findByRoomId(roomId);
        if(room==null){
            throw new RuntimeException("Room not found");
        }
        return room;
    }

    @Override
    public List<Message> getMessage(String roomId, int page, int size) {
        Room room=roomRepo.findByRoomId(roomId);
        if(room==null){
            throw new RuntimeException("Room not found");
        }
        List<Message> messages=room.getMessage();
        int start=Math.max(0,messages.size()-(page+1)*size);
        int end=Math.min(messages.size(),start+size);
        List<Message> paginatedMessages= messages.subList(start,end);
        return paginatedMessages;
    }

    @Override
    public void deleteRoom(String roomId) {
        Room room=roomRepo.findByRoomId(roomId);
        if(room==null){
            throw new RuntimeException("Room not found");
        }
        roomRepo.delete(room);
        logger.info("Room deleted with ID: "+roomId);

    }

    @Override
    public ResponseEntity<String> deleteMessage(String roomId, String messageId) {
        Room room = roomRepo.findByRoomId(roomId);

        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Room not found");
        }

        List<Message> messages = room.getMessage();
        Message messageToDelete = null;

        for (Message message : messages) {
            if (message.getMessageId() != null && message.getMessageId().equals(messageId)) {
                messageToDelete = message;
                break;
            }
        }

        if (messageToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Message not found in this room");
        }

        messages.remove(messageToDelete);
        roomRepo.save(room);
        logger.info("Message deleted with ID: " + messageId + " from room: " + roomId);

        return ResponseEntity.ok("Message deleted successfully");
    }

}
