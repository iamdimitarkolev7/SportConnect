package com.connect.sport.authentication.event;

import com.connect.sport.authentication.payload.kafka.event.Event;
import com.connect.sport.authentication.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseEventHandler {

    private final UserService userService;

    @KafkaListener(topics = "post-create-topic", groupId = "mitko")
    private void eventListenerHandler(Event event) {

        switch (event.getEventType()) {

            case CREATE_POST_EVENT -> {

            }
            case DELETE_POST_EVENT -> {
                System.out.println(event);
            }
            case AUTHORIZE_TOKEN_EVENT -> {
                System.out.println("Auth");
            }
        }
    }
}
