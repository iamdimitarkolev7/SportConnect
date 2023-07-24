package com.connect.sport.authentication.model;

import com.connect.sport.authentication.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Token {

    @Id
    private String id;
    private String userId;
    private String token;
    private String refreshToken;
    private String ipAddress;
    private String userAgent;

    private LocalDateTime tokenExpiry;
    private LocalDateTime refreshTokenExpiry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastActivity;

    private boolean active;
}
