package com.connect.sport.authentication.repository;

import com.connect.sport.authentication.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

    Optional<Token> findByToken(String token);
    List<Token> findAllByUserId(String userId);
}
