package com.connect.sport.posts.service.interfaces;

import java.io.IOException;

public interface TokenService {

    void authorizeToken(String token) throws IOException;
}
