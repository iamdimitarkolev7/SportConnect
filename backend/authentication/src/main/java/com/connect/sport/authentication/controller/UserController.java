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

    @PostMapping("/api/users/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserRegisterRequest request) {

        return ResponseEntity.ok(
                Response.builder()
                        .message("Test register")
                        .build()
        );
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<Response> loginUser(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(
                Response.builder()
                        .message("Test login")
                        .build()
        );
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(
                Response.builder()
                        .message("Test get user")
                        .data(of("userId", userId))
                        .build()
        );
    }

    @GetMapping("/api/users/logout")
    public ResponseEntity<Response> logoutUser() {
        return ResponseEntity.ok(
                Response.builder()
                        .message("Test logout user")
                        .build()
        );
    }
}
