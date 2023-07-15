package com.connect.sport.authentication.controller;

import com.connect.sport.authentication.exception.verification.VerificationFailedException;
import com.connect.sport.authentication.model.User;
import com.connect.sport.authentication.model.request.UserLoginRequest;
import com.connect.sport.authentication.model.request.UserRegisterRequest;
import com.connect.sport.authentication.model.response.Response;
import com.connect.sport.authentication.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserRegisterRequest request) {

        try {
            User user = userService.registerUser(request);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .timeStamp(LocalDateTime.now())
                            .data(of("registeredUser", user))
                            .message("Registration is successful")
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

    @GetMapping("/api/v1/auth/verify")
    public ResponseEntity<Response> verifyUser(@RequestParam("token") String token,
                                               @RequestParam("email") String email) {

        try {

            userService.verifyUser(token, email);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .timeStamp(LocalDateTime.now())
                            .message("User successfully verified")
                            .build()
            );
        }
        catch(VerificationFailedException e) {

            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .success(false)
                            .timeStamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<Response> loginUser(@RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(
                Response.builder()
                        .message("Test login")
                        .build()
        );
    }

    @GetMapping("/api/v1/auth/logout")
    public ResponseEntity<Response> logoutUser() {
        return ResponseEntity.ok(
                Response.builder()
                        .message("Test logout user")
                        .build()
        );
    }
}
