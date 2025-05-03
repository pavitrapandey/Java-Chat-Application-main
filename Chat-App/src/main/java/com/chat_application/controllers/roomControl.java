package com.chat_application.controllers;

import com.chat_application.Service.roomService;
import com.chat_application.config.AppConstants;
import com.chat_application.entities.Message;
import com.chat_application.entities.Room;
import com.chat_application.repositories.roomRepo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin(AppConstants.FRONT_END_URL)
public class roomControl {

    @Autowired
    private roomService roomService;

    Logger logger= org.slf4j.LoggerFactory.getLogger(chatControl.class);
    //create room

   @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId){
        Room room=roomService.createRoom(roomId);
       return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){
       Room room=roomService.joinRoom(roomId);
        logger.info("Room joined with ID: "+roomId);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessage(@PathVariable String roomId,
                                                    @RequestParam(value ="page",defaultValue = "0",required = false) int page,
                                                    @RequestParam(value = "size",defaultValue = "20",required = false) int size){
        List<Message> paginatedMessages=roomService.getMessage(roomId,page,size);
       return ResponseEntity.ok(paginatedMessages);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId){
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room Deleted");
    }

    @DeleteMapping("/{roomId}/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(
            @PathVariable String roomId,
            @PathVariable String messageId) {

       roomService.deleteMessage(roomId,messageId);

        return ResponseEntity.ok("Message deleted successfully");
    }


}
