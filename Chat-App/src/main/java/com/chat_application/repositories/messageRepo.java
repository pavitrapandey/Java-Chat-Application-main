package com.chat_application.repositories;

import com.chat_application.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface messageRepo extends MongoRepository<Message,String> {

    //get message using message ID
    Message findByMessageId(String messageId);

}
