package com.chat_application.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    private String messageId;
    private String sender;
    private String content;
    private LocalDateTime timeStamp;

    public Message(String sender,String content){
        this.sender=sender;
        this.content=content;
        this.timeStamp=LocalDateTime.now();
    }
}
