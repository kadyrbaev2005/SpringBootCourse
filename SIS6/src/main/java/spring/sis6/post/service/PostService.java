package spring.sis6.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.sis6.event.PostCreatedEvent;
import spring.sis6.exception.ResourceNotFoundException;
import spring.sis6.post.dto.CreatePostRequest;
import spring.sis6.post.entity.Post;
import spring.sis6.post.kafka.PostProducer;
import spring.sis6.post.repository.PostRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostProducer postProducer;

    public Post createPost(CreatePostRequest request) {
        log.info("Creating post for user: {}", request.getUserId());

        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(request.getUserId());
        post.setContent(request.getContent());
        post.setHashtags(request.getHashtags() != null ?
                String.join(",", request.getHashtags()) : "");
        post.setStatus("PUBLISHED");
        post.setCreatedAt(LocalDateTime.now());

        Post saved = postRepository.save(post);
        log.debug("Post saved with id: {}", saved.getId());

        PostCreatedEvent event = new PostCreatedEvent(
                saved.getId().toString(),
                saved.getUserId(),
                saved.getContent(),
                request.getHashtags(),
                saved.getCreatedAt()
        );
        postProducer.publishPost(event);

        return saved;
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post not found with id: " + id));
    }
}