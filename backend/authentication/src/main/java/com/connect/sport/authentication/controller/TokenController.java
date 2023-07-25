package com.connect.sport.authentication.controller;

import com.connect.sport.authentication.model.Token;
import com.connect.sport.authentication.payload.response.Response;
import com.connect.sport.authentication.service.interfaces.TokenService;
import com.connect.sport.authentication.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    private final JwtService jwtService;

    @PostMapping("/api/v1/token/refresh-token")
    private ResponseEntity<Response> refreshJwtToken(@RequestHeader("Authorization") String authorizationHeader) {

        try {

            String refreshToken = jwtService.extractToken(authorizationHeader);
            Token token = tokenService.generateNewToken(refreshToken);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .data(of("newJwt", token))
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .success(false)
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }
}
