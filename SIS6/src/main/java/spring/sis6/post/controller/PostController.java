package spring.sis6.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import spring.sis6.post.dto.CreatePostRequest;
import spring.sis6.post.entity.Post;
import spring.sis6.post.service.PostService;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Post API", description = "Operations for creating and reading posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Publish a new post")
    @ApiResponse(responseCode = "201", description = "Post published successfully")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @PostMapping
    public ResponseEntity<Post> createPost(
            @Valid @RequestBody CreatePostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(request));
    }

    @Operation(summary = "Get post by ID")
    @ApiResponse(responseCode = "200", description = "Post found")
    @ApiResponse(responseCode = "404", description = "Post not found")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getById(@PathVariable UUID postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }
}   