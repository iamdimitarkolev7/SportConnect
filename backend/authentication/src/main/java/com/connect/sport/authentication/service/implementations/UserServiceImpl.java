package com.connect.sport.authentication.service.implementations;

import com.connect.sport.authentication.enums.UserRole;
import com.connect.sport.authentication.exception.InvalidCredentialsException;
import com.connect.sport.authentication.exception.PasswordDoNotMatchException;
import com.connect.sport.authentication.exception.UserAlreadyExistsException;
import com.connect.sport.authentication.exception.UserNotFoundException;
import com.connect.sport.authentication.exception.verification.UserNotVerifiedException;
import com.connect.sport.authentication.exception.verification.VerificationFailedException;
import com.connect.sport.authentication.utils.jwt.JwtService;
import com.connect.sport.authentication.model.User;
import com.connect.sport.authentication.model.request.UserLoginRequest;
import com.connect.sport.authentication.model.request.UserRegisterRequest;
import com.connect.sport.authentication.model.token.Token;
import com.connect.sport.authentication.repository.UserRepository;
import com.connect.sport.authentication.service.interfaces.UserService;
import com.connect.sport.authentication.utils.verification.email.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final EmailVerificationService emailVerificationService;

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public boolean userExists(Long userId) {
        return false;
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public User registerUser(UserRegisterRequest request) {

        final String username = request.getUsername();
        Optional<User> o_user = userRepository.findByUsername(username);

        if (o_user.isPresent()) {

            User existingUser = o_user.get();

            if (existingUser.isEnabled()) {
                throw new UserAlreadyExistsException("User with username " + username + " already exists!");
            }
            else {
                userRepository.delete(existingUser);
            }
        }

        final String password = request.getPassword();
        final String confirmPassword = request.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new PasswordDoNotMatchException("Passwords do not match!");
        }

        String verificationToken = emailVerificationService.generateVerificationToken();

        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .role(UserRole.USER)
                .enabled(false)
                .userTokens(new ArrayList<>())
                .verificationCode(verificationToken)
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();

        Token userToken = Token.builder()
                .userID(newUser.getId())
                .token(jwtService.generateToken(newUser))
                .expired(false)
                .revoked(false)
                .build();

        newUser.addToken(userToken);

        User createdUser = userRepository.save(newUser);

        emailVerificationService.sendVerificationEmail(createdUser, createdUser.getVerificationCode());

        return createdUser;
    }

    @Override
    public void verifyUser(String verificationToken, String email) {

        Optional<User> o_user = userRepository.findByEmail(email);

        if (o_user.isEmpty()) {
            throw new VerificationFailedException("Verification failed");
        }

        User user = o_user.get();

        if (user.getVerificationCode().equals(verificationToken)) {
            user.setEnabled(true);
            userRepository.save(user);
        }
        else {
            throw new VerificationFailedException("Verification failed");
        }
    }

    @Override
    public User loginUser(UserLoginRequest request) {

        final String username = request.getUsername();
        Optional<User> o_user = userRepository.findByUsername(username);

        if (userRepository.findByUsername(username).isEmpty()) {
            throw new UserNotFoundException("User with username " + username + " does not exist!");
        }

        User user = o_user.get();

        if (!user.isEnabled()) {
            throw new UserNotVerifiedException("User is not verified! Check your email!");
        }

        boolean passwordsMatch = bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordsMatch) {
            throw new InvalidCredentialsException("Invalid password!");
        }

        Token userToken = Token.builder()
                .userID(user.getId())
                .token(jwtService.generateToken(user))
                .expired(false)
                .revoked(false)
                .build();

        user.setUserTokens(List.of(userToken));

        return userRepository.save(user);
    }

    @Override
    public void logoutUser(String token) {

        String username = jwtService.extractUsername(token.substring(7));
        Optional<User> o_user = userRepository.findByUsername(username);

        if (o_user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        User user = o_user.get();

        user.setUserTokens(new ArrayList<>());
    }
}
