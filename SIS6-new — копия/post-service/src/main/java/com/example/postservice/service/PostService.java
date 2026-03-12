package com.example.postservice.service;

import com.example.postservice.dto.CreatePostRequest;
import com.example.postservice.dto.PostResponse;
import com.example.postservice.entity.Post;
import com.example.postservice.entity.PostStatus;
import com.example.postservice.event.PostCreatedEvent;
import com.example.postservice.repository.PostRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PostService(PostRepository postRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.postRepository = postRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        // Generate unique post ID
        UUID postId = UUID.randomUUID();

        // Create and save post entity
        Post post = new Post();
        post.setId(postId);
        post.setUserId(request.getUserId());
        post.setContent(request.getContent());
        post.setHashtags(request.getHashtags() != null ?
                String.join(",", request.getHashtags()) : null);
        post.setStatus(PostStatus.PUBLISHED);
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        // Publish event to Kafka
        PostCreatedEvent event = new PostCreatedEvent(
                savedPost.getId(),
                savedPost.getUserId(),
                savedPost.getContent(),
                request.getHashtags(),
                savedPost.getCreatedAt()
        );

        kafkaTemplate.send("posts", event);

        // Convert to response DTO
        return convertToResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return convertToResponse(post);
    }

    private PostResponse convertToResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setUserId(post.getUserId());
        response.setContent(post.getContent());
        response.setHashtags(post.getHashtags() != null ?
                List.of(post.getHashtags().split(",")) : null);
        response.setStatus(post.getStatus().toString());
        response.setCreatedAt(post.getCreatedAt());
        return response;
    }
}