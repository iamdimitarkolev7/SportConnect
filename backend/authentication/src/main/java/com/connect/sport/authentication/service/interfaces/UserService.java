package com.connect.sport.authentication.service.interfaces;

import com.connect.sport.authentication.model.Token;
import com.connect.sport.authentication.model.User;
import com.connect.sport.authentication.payload.request.UserLoginRequest;
import com.connect.sport.authentication.payload.request.UserRegisterRequest;
import reactor.util.function.Tuple2;

import java.net.UnknownHostException;
import java.util.Optional;

public interface UserService {

    User editUser(User user);
    User delete(User user);
    User getUserById(String userId);
    User getUserByUsername(String username);
    boolean userExists(Long userId);
    boolean userExists(String username);
    User registerUser(UserRegisterRequest request);
    void verifyUser(final String verificationToken, final String email);
    Token loginUser(UserLoginRequest request) throws UnknownHostException;
    void logoutUser(final String token);
}
