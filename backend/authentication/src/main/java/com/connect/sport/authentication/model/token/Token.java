package com.connect.sport.authentication.model.token;

import com.connect.sport.authentication.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Token {

    @Id
    private String id;

    @Column(unique = true)
    private String token;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;
    private boolean expired;

    private String userID;
}
