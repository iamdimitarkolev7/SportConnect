package com.connect.sport.posts.controller;

import com.connect.sport.posts.exception.InvalidIdException;
import com.connect.sport.posts.model.Post;
import com.connect.sport.posts.service.interfaces.PostService;
import com.connect.sport.posts.payload.request.PostRequest;
import com.connect.sport.posts.payload.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/v1/post/get/{id}")
    public ResponseEntity<Response> getPostById(@PathVariable("id") String id) {

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
    public ResponseEntity<Response> createPost(@RequestBody PostRequest request) throws IOException {

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

    @PostMapping("/api/v1/post/archive/{id}")
    public ResponseEntity<Response> archivePost(@PathVariable("id") String id) {

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
    public ResponseEntity<Response> editPost(@PathVariable("id") String id,
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
    public ResponseEntity<Response> deletePost(@PathVariable("id") String id) {

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
}
