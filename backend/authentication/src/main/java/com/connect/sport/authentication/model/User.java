package com.connect.sport.authentication.model;

import com.connect.sport.authentication.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    private UserRole userRole;
}
