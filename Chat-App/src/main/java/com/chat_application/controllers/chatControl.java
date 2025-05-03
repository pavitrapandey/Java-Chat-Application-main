package com.chat_application.controllers;


import com.chat_application.config.AppConstants;
import com.chat_application.entities.Message;
import com.chat_application.entities.Room;
import com.chat_application.playload.MessageRequest;
import com.chat_application.repositories.roomRepo;
import org.slf4j.Logger;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@CrossOrigin(AppConstants.FRONT_END_URL)
public class chatControl {
    private roomRepo roomRepo;

    Logger logger= org.slf4j.LoggerFactory.getLogger(chatControl.class);

    public chatControl(roomRepo roomRepo){
        this.roomRepo=roomRepo;
    }

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
    ){
        Room room=roomRepo.findByRoomId(request.getRoomId());
        Message message=new Message();
        message.setMessageId(UUID.randomUUID().toString());
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());


        if(room!=null){
            room.getMessage().add(message);
            roomRepo.save(room);
            logger.info("Message sent to room: "+roomId);
        }else{
            throw new RuntimeException("Room not found");
        }

        return message;
    }

}
