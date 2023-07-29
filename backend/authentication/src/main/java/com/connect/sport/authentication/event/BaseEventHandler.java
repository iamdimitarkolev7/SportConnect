package com.connect.sport.authentication.event;

import com.connect.sport.authentication.payload.kafka.event.Event;
import com.connect.sport.authentication.service.interfaces.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class BaseEventHandler {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    @KafkaListener(topics = "post-create-topic", groupId = "mitko", containerFactory = "kafkaListenerContainerFactory")
    private void eventListenerHandler(byte[] receivedBytes) throws IOException {

        Event receivedEvent = Event.fromJsonBytes(receivedBytes);
        String jsonString = objectMapper.writeValueAsString(receivedEvent.getData());

        switch (receivedEvent.getEventType()) {
            case CREATE_POST_EVENT -> handlePostCreateEvent(jsonString);
            case DELETE_POST_EVENT -> handlePostDeleteEvent(jsonString);
        }
    }

    private void handlePostCreateEvent(String jsonString) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        JsonNode dataNode = jsonNode.get("created-post");
        String postId = dataNode.get("id").asText();
        String authorId = dataNode.get("authorId").asText();

        if (postId != null && authorId != null) {
            userService.addPostId(authorId, postId);
        }
    }

    private void handlePostDeleteEvent(String jsonString) {

        System.out.println(jsonString);
    }
}
