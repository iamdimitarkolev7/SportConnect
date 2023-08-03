package com.connect.sport.authentication.service.interfaces;

import com.connect.sport.authentication.model.Token;

public interface TokenService {

    Token generateNewToken(String refreshToken);
    boolean validateToken(String bearerToken);
}
