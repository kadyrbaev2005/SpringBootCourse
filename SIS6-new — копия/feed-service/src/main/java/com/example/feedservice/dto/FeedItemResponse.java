package com.example.feedservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Feed item response object")
public class FeedItemResponse {

    @Schema(description = "Feed item ID", example = "1")
    private Long id;

    @Schema(description = "Post ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID postId;

    @Schema(description = "User ID", example = "user-42")
    private String userId;

    @Schema(description = "Post content", example = "Hello Kafka!")
    private String content;

    @Schema(description = "List of hashtags", example = "[\"kafka\", \"spring\"]")
    private List<String> hashtags;

    @Schema(description = "Post creation timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "When the item was added to feed", example = "2024-01-15T10:30:05")
    private LocalDateTime receivedAt;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getPostId() { return postId; }
    public void setPostId(UUID postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getHashtags() { return hashtags; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
}