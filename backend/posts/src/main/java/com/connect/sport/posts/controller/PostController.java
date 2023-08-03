package com.connect.sport.posts.controller;

import com.connect.sport.posts.exception.InvalidIdException;
import com.connect.sport.posts.exception.TokenExpiredException;
import com.connect.sport.posts.model.Post;
import com.connect.sport.posts.service.interfaces.PostService;
import com.connect.sport.posts.payload.request.PostRequest;
import com.connect.sport.posts.payload.response.Response;
import com.connect.sport.posts.service.interfaces.TokenService;
import com.connect.sport.posts.utils.KafkaResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final KafkaResponse kafkaResponse;

    private final PostService postService;
    private final TokenService tokenService;

    @GetMapping("/api/v1/post/get/{id}")
    public ResponseEntity<Response> getPostById(@RequestHeader("Authorization") String token,
                                                @PathVariable("id") String id) {

        try {

            Post post = postService.getPostById(id);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .timeStamp(LocalDateTime.now())
                            .message("Post successfully retrieved")
                            .data(of("post", post))
                            .build()
            );
        }
        catch (InvalidIdException e) {

            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .success(false)
                            .timeStamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PostMapping("/api/v1/post/create")
    public ResponseEntity<Response> createPost(@RequestHeader("Authorization") String token,
                                               @RequestBody PostRequest request) {


        try {

            authorizeToken(token);
            Post createdPost = postService.createPost(request);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .timeStamp(LocalDateTime.now())
                            .message("Post created successfully")
                            .data(of("createdPost", createdPost))
                            .build()
            );
        }
        catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .success(false)
                            .timeStamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PostMapping("/api/v1/post/archive/{id}")
    public ResponseEntity<Response> archivePost(@RequestHeader("Authorization") String token,
                                                @PathVariable("id") String id) {

        try {

            Post archivedPost = postService.archivePost(id);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .timeStamp(LocalDateTime.now())
                            .message("Post successfully archived!")
                            .data(of("archivedPost", archivedPost))
                            .build()
            );
        }
        catch (InvalidIdException e) {

            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .success(false)
                            .timeStamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PutMapping("/api/v1/post/edit/{id}")
    public ResponseEntity<Response> editPost(@RequestHeader("Authorization") String token,
                                             @PathVariable("id") String id,
                                             @RequestBody PostRequest request) {

        try {

            Post editedPost = postService.editPost(id, request);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .timeStamp(LocalDateTime.now())
                            .message("Post edited successfully!")
                            .data(of("editedPost", editedPost))
                            .build()
            );
        }
        catch (InvalidIdException e) {

            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .success(false)
                            .timeStamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @DeleteMapping("/api/v1/post/delete/{id}")
    public ResponseEntity<Response> deletePost(@RequestHeader("Authorization") String token,
                                               @PathVariable("id") String id) {

        try {
            postService.deletePost(id);

            return ResponseEntity.ok(
                    Response.builder()
                            .success(true)
                            .timeStamp(LocalDateTime.now())
                            .message("Post successfully deleted!")
                            .build()
            );
        }
        catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .success(false)
                            .timeStamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .build()
            );
        }
    }


    private void authorizeToken(String token) throws IOException {

        tokenService.authorizeToken(token);
        JsonNode jsonNode = kafkaResponse.getResponse();

        if (jsonNode.has("error")) {
            throw new TokenExpiredException(jsonNode.get("error").asText());
        }
    }
}
