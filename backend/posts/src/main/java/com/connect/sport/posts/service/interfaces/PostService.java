package com.connect.sport.posts.service.interfaces;

import com.connect.sport.posts.model.Post;
import com.connect.sport.posts.utils.request.PostRequest;

import java.util.List;

public interface PostService {

    Post getPostById(String postId);
    Post createPost(PostRequest request);
    Post archivePost(String postId);
    Post editPost(String postId, PostRequest post);
    void deletePost(String postId);
    List<Post> getPostsByUserId(String userId);
}
