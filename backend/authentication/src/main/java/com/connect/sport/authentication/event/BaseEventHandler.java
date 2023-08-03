package com.connect.sport.authentication.event;

import com.connect.sport.authentication.enums.EventType;
import com.connect.sport.authentication.exception.jwt.TokenExpiredException;
import com.connect.sport.authentication.payload.kafka.event.Event;
import com.connect.sport.authentication.service.interfaces.TokenService;
import com.connect.sport.authentication.service.interfaces.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class BaseEventHandler {

    @Autowired
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final TokenService tokenService;

    @KafkaListener(topics = {"authorize-token-request-topic", "post-create-topic"}, groupId = "mitko", containerFactory = "kafkaListenerContainerFactory")
    private void eventListenerHandler(byte[] receivedBytes) throws IOException {

        Event receivedEvent = Event.fromJsonBytes(receivedBytes);
        String jsonString = objectMapper.writeValueAsString(receivedEvent.getData());

        switch (receivedEvent.getEventType()) {
            case CREATE_POST_EVENT -> handlePostCreateEvent(jsonString);
            case DELETE_POST_EVENT -> handlePostDeleteEvent(jsonString);
            case AUTHORIZE_TOKEN_REQUEST_EVENT -> handleAuthorizeTokenRequestEvent(jsonString);
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

    private void handleAuthorizeTokenRequestEvent(String jsonString) throws IOException {

        JsonNode jsonNode = objectMapper.readTree(jsonString);
        String bearerToken = jsonNode.get("token").asText();
        Map<String, String> data = new HashMap<>();

        try {
            String isValid = String.valueOf(tokenService.validateToken(bearerToken));
            data.put("token-authorized", isValid);
        }
        catch (TokenExpiredException e) {
            data.put("error", e.getMessage());
        }

        Event authTokenEvent = new Event(EventType.AUTHORIZE_TOKEN_RESPONSE_EVENT, data);
        byte[] eventBytes = authTokenEvent.toJsonBytes();
        kafkaTemplate.send("authorize-token-response-topic", eventBytes);
    }
}
