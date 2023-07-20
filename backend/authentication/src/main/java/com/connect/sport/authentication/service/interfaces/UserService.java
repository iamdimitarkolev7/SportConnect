package com.connect.sport.authentication.service.interfaces;

import com.connect.sport.authentication.model.User;
import com.connect.sport.authentication.model.request.UserLoginRequest;
import com.connect.sport.authentication.model.request.UserRegisterRequest;

import java.util.Optional;

public interface UserService {

    User editUser(User user);
    User delete(User user);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByUsername(String username);
    boolean userExists(Long userId);
    boolean userExists(String username);
    User registerUser(UserRegisterRequest request);
    void verifyUser(final String verificationToken, final String email);
    User loginUser(UserLoginRequest request);
    void logoutUser(final String token);
}
