package com.connect.sport.authentication.service.implementations;

import com.connect.sport.authentication.exception.jwt.InvalidTokenException;
import com.connect.sport.authentication.exception.jwt.TokenExpiredException;
import com.connect.sport.authentication.model.Token;
import com.connect.sport.authentication.repository.TokenRepository;
import com.connect.sport.authentication.service.interfaces.TokenService;
import com.connect.sport.authentication.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    private final JwtService jwtService;

    public Token generateNewToken(String refreshToken) {

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new InvalidTokenException("Refresh token is expired!");
        }

        String userId = jwtService.extractUserId(refreshToken);
        List<Token> userTokens = tokenRepository.findAllByUserId(userId);

        Token userActiveToken = userTokens.stream()
                .filter(Token::isActive)
                .findFirst()
                .orElse(null);

        if (userActiveToken == null) {
            throw new InvalidTokenException("Refresh token is invalid!");
        }

        String username = jwtService.extractUsername(refreshToken);

        userActiveToken.setToken(jwtService.generateNewToken(username, userId));

        return tokenRepository.save(userActiveToken);
    }

    @Override
    public boolean validateToken(String bearerToken) {

        String jwt = extractToken(bearerToken);

        if (jwtService.isTokenExpired(jwt)) {
            throw new TokenExpiredException("The jwt token is expired!");
        }

        return true;
    }

    private String extractToken(String bearerToken) {
        return bearerToken.substring(7);
    }
}
