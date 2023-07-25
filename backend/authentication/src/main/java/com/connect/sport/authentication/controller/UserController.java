package com.connect.sport.authentication.controller;

import com.connect.sport.authentication.exception.jwt.InvalidTokenException;
import com.connect.sport.authentication.model.User;
import com.connect.sport.authentication.payload.response.Response;
import com.connect.sport.authentication.service.interfaces.UserService;
import com.connect.sport.authentication.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private final JwtService jwtService;

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<Response> getUserById(@RequestHeader("Authorization") String authorizationHeader,
                                                @PathVariable("id") String userId) {

        try {

            if (!jwtService.authHeaderValid(authorizationHeader)) {
                throw new InvalidTokenException("JWT expired!");
            }

            User user = userService.getUserById(userId);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .timeStamp(LocalDateTime.now())
                            .message("User successfully retrieved!")
                            .data(of("user", user))
                            .build()
            );
        }
        catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .success(false)
                            .timeStamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .build()
            );
        }
    }
}
