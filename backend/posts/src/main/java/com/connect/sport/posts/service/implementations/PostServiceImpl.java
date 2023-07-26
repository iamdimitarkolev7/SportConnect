package com.connect.sport.posts.service.implementations;

import com.connect.sport.posts.enums.EventType;
import com.connect.sport.posts.enums.Status;
import com.connect.sport.posts.exception.InvalidIdException;
import com.connect.sport.posts.model.Post;
import com.connect.sport.posts.payload.kafka.event.Event;
import com.connect.sport.posts.repository.PostRepository;
import com.connect.sport.posts.service.interfaces.PostService;
import com.connect.sport.posts.payload.request.PostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    private final PostRepository postRepository;

    @Override
    public Post getPostById(String postId) {

        Optional<Post> o_post = postRepository.findById(postId);

        if (o_post.isEmpty()) {
            throw new InvalidIdException("There is no post with such id!");
        }

        return o_post.get();
    }

    @Override
    public Post createPost(PostRequest request) {

        Post post = Post.builder()
                .authorId(request.getAuthorId())
                .timestamp(request.getTimestamp())
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .sportType(request.getSportType())
                .status(request.getStatus())
                .visibility(request.getVisibility())
                .tags(request.getTags())
                .build();

        Map<String, Post> createdPost = new HashMap<>();
        createdPost.put("created-post", post);

        Event postCreateEvent = new Event(EventType.CREATE_POST_EVENT, createdPost);
        kafkaTemplate.send("post-create-topic", postCreateEvent);

        return postRepository.save(post);
    }

    @Override
    public Post archivePost(String postId) {

        Optional<Post> o_post = postRepository.findById(postId);

        if (o_post.isEmpty()) {
            throw new InvalidIdException("There is no post with such id!");
        }

        Post post = o_post.get();
        post.setStatus(Status.ARCHIVED);

        return postRepository.save(post);
    }

    @Override
    public Post editPost(String postId, PostRequest request) {

        Optional<Post> o_post = postRepository.findById(postId);

        if (o_post.isEmpty()) {
            throw new InvalidIdException("There is no post with such id!");
        }

        Post post = o_post.get();
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setLocation(request.getLocation());
        post.setTags(request.getTags());
        post.setSportType(request.getSportType());
        post.setVisibility(request.getVisibility());

        return postRepository.save(post);
    }

    @Override
    public void deletePost(String id) {

        if (!postRepository.existsById(id)) {
            throw new InvalidIdException("There is no post with such id!");
        }

        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostsByUserId(String userId) {
        return null;
    }
}
