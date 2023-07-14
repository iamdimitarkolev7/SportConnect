package com.connect.sport.authentication.repository;

import com.connect.sport.authentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    <S extends User> S save(S entity);

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    long count();

    void delete(User entity);

    boolean existsById(Long primaryKey);
}
