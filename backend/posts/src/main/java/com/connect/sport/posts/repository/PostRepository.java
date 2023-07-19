package com.connect.sport.posts.repository;

import com.connect.sport.posts.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {

    <S extends Post> S save(S post);
    <S extends Post> S insert(S post);
    Optional<Post> findById(String id);
    void delete(Post entity);
    List<Post> findAllById(Iterable<String> ids);
    boolean existsById(String s);
}
