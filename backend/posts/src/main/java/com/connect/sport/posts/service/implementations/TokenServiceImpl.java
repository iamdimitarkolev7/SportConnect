package com.connect.sport.posts.service.implementations;

import com.connect.sport.posts.enums.EventType;
import com.connect.sport.posts.payload.kafka.event.Event;
import com.connect.sport.posts.service.interfaces.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Autowired
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Override
    public void authorizeToken(String token) throws IOException {

        Map<String, String> data = new HashMap<>();
        data.put("token", token);

        Event tokenVerifyEvent = new Event(EventType.AUTHORIZE_TOKEN_REQUEST_EVENT, data);
        byte[] eventBytes = tokenVerifyEvent.toJsonBytes();

        kafkaTemplate.send("authorize-token-request-topic", eventBytes);
    }
}
