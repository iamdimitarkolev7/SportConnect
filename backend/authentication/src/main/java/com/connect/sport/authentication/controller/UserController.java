package com.connect.sport.authentication.controller;

import com.connect.sport.authentication.model.request.UserLoginRequest;
import com.connect.sport.authentication.model.request.UserRegisterRequest;
import com.connect.sport.authentication.model.response.Response;
import com.connect.sport.authentication.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Map.of;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(
                Response.builder()
                        .message("Test get user")
                        .data(of("userId", userId))
                        .build()
        );
    }
}
