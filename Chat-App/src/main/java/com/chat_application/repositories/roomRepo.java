package com.chat_application.repositories;

import com.chat_application.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface roomRepo extends MongoRepository<Room,String> {

    //get room using room ID
    Room findByRoomId(String roomId);

}
