package com.connect.sport.posts.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class KafkaResponse {

    private JsonNode response;
}
