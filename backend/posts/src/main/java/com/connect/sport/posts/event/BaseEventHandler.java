package com.connect.sport.posts.event;

import com.connect.sport.posts.payload.kafka.event.Event;
import com.connect.sport.posts.service.interfaces.TokenService;
import com.connect.sport.posts.utils.KafkaResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class BaseEventHandler {

    @Autowired
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private final KafkaResponse kafkaResponse;

    private final ObjectMapper objectMapper;
    private final TokenService tokenService;

    @KafkaListener(topics = "authorize-token-response-topic", groupId = "mitko", containerFactory = "kafkaListenerContainerFactory")
    public void receiveResponse(byte[] receivedBytes) throws IOException {
        Event receivedEvent = Event.fromJsonBytes(receivedBytes);
        String jsonString = objectMapper.writeValueAsString(receivedEvent.getData());
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        kafkaResponse.setResponse(jsonNode);

    }
}
