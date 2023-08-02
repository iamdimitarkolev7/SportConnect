package com.connect.sport.authentication.service.implementations;

import com.connect.sport.authentication.enums.UserRole;
import com.connect.sport.authentication.exception.*;
import com.connect.sport.authentication.exception.jwt.InvalidTokenException;
import com.connect.sport.authentication.exception.verification.UserNotVerifiedException;
import com.connect.sport.authentication.exception.verification.VerificationFailedException;
import com.connect.sport.authentication.repository.TokenRepository;
import com.connect.sport.authentication.utils.jwt.JwtService;
import com.connect.sport.authentication.model.User;
import com.connect.sport.authentication.payload.request.UserLoginRequest;
import com.connect.sport.authentication.payload.request.UserRegisterRequest;
import com.connect.sport.authentication.model.Token;
import com.connect.sport.authentication.repository.UserRepository;
import com.connect.sport.authentication.service.interfaces.UserService;
import com.connect.sport.authentication.utils.verification.email.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final EmailVerificationService emailVerificationService;

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public User getUserById(String userId) {

        Optional<User> o_user = userRepository.findById(userId);

        if (o_user.isEmpty()) {
            throw new UserNotFoundException("There is no user with such id!");
        }

        return o_user.get();
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
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
        final Optional<User> o_user = userRepository.findByUsername(username);
        if (o_user.isPresent()) {
            User existingUser = o_user.get();
            validateUser(existingUser);
        }

        final String email = request.getEmail();
        validateEmail(email);

        final String password = request.getPassword();
        final String confirmPassword = request.getConfirmPassword();
        validatePassword(password, confirmPassword);

        final String verificationToken = emailVerificationService.generateVerificationToken();
        User newUser = buildNewUser(request, verificationToken);
        User createdUser = userRepository.save(newUser);
        emailVerificationService.sendVerificationEmail(createdUser, createdUser.getVerificationCode());

        return createdUser;
    }

    @Override
    public void verifyUser(final String verificationToken, final String email) {

        Optional<User> o_user = userRepository.findByEmail(email);
        if (o_user.isEmpty()) {
            throw new VerificationFailedException("Verification failed");
        }

        User user = o_user.get();
        checkVerificationCode(user, verificationToken);
    }

    @Override
    public Token loginUser(UserLoginRequest request) throws UnknownHostException {

        final String email = request.getEmail();
        final String username = request.getUsername();
        validateUsernameOrEmailInput(email, username);

        final Optional<User> o_user = !(email == null || email.isEmpty()) ?
                userRepository.findByEmail(email) :
                userRepository.findByUsername(username);

        if (o_user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        User user = o_user.get();
        checkVerification(user);

        checkLoginPasswordMatch(request.getPassword(), user.getPassword());

        Token userToken = generateUserToken(user);
        userRepository.save(user);

        return tokenRepository.save(userToken);
    }

    @Override
    public void logoutUser(final String token) {

        final String jwt = token.substring(7);
        final String userId = jwtService.extractUserId(jwt);

        Optional<Token> o_currentToken = tokenRepository.findByToken(jwt);

        if (o_currentToken.isEmpty() || userId.isEmpty()) {
            throw new InvalidTokenException("The provided bearer token is invalid!");
        }

        Token currentToken = o_currentToken.get();
        currentToken.setActive(false);
        currentToken.setLastActivity(LocalDateTime.now());
        tokenRepository.save(currentToken);
        SecurityContextHolder.clearContext();
    }

    @Override
    public void addPostId(String userId, String postId) {

        Optional<User> o_user = userRepository.findById(userId);

        if (o_user.isEmpty()) {
            throw new UserNotFoundException("There is no user with such id!");
        }

        User user = o_user.get();
        user.addPostId(postId);
    }

    @Override
    public void deletePostId(String userId, String postId) {

    }

    private void validateUser(User user) {

        if (user.isEnabled()) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists!");
        }
        else {
            userRepository.delete(user);
        }
    }

    private User buildNewUser(UserRegisterRequest request, final String verificationToken) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .role(UserRole.USER)
                .enabled(false)
                .verificationCode(verificationToken)
                .postIds(new ArrayList<>())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();
    }

    private void validatePassword(final String password, final String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            throw new PasswordDoNotMatchException("Passwords do not match!");
        }
    }

    private void validateEmail(final String email) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyUsedException("This email is already used!");
        }
    }

    private void checkVerificationCode(User user, final String verificationCode) {

        if (user.getVerificationCode().equals(verificationCode)) {
            user.setEnabled(true);
            user.setVerificationCode(null);
            userRepository.save(user);
        }
        else {
            throw new VerificationFailedException("Verification failed");
        }
    }

    private void checkVerification(User user) {

        if (!user.isEnabled()) {
            throw new UserNotVerifiedException("User is not verified! Check your email!");
        }
    }

    private void checkLoginPasswordMatch(final String requestPassword, final String userPassword) {

        if (!bCryptPasswordEncoder.matches(requestPassword, userPassword)) {
            throw new PasswordDoNotMatchException("Wrong password!");
        }
    }

    private void validateUsernameOrEmailInput(final String email, final String username) {

        if ((email == null || email.isEmpty()) && (username == null || username.isEmpty())) {
            throw new InvalidCredentialsException("Invalid username/email!");
        }
    }

    private Token generateUserToken(User user) throws UnknownHostException {

        return Token.builder()
                .userId(user.getId())
                .token(jwtService.generateToken(user))
                .tokenExpiry(LocalDateTime.now().plus(jwtService.getJwtExpiration(), ChronoUnit.MILLIS))
                .refreshToken(jwtService.generateRefreshToken(user))
                .refreshTokenExpiry(LocalDateTime.now().plus(jwtService.getRefreshExpiration(), ChronoUnit.MILLIS))
                .ipAddress(Inet6Address.getLocalHost().getHostAddress())
                .userAgent("SHOULD ADD THIS")
                .createdAt(LocalDateTime.now())
                .lastActivity(LocalDateTime.now())
                .active(true)
                .build();
    }
}
