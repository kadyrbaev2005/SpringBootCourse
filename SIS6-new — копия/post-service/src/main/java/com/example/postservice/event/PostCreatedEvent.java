package com.example.postservice.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PostCreatedEvent {
    private UUID postId;
    private String userId;
    private String content;
    private List<String> hashtags;
    private LocalDateTime timestamp;

    public PostCreatedEvent() {}

    public PostCreatedEvent(UUID postId, String userId, String content,
                            List<String> hashtags, LocalDateTime timestamp) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.hashtags = hashtags;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public UUID getPostId() { return postId; }
    public void setPostId(UUID postId) { this.postId = postId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getHashtags() { return hashtags; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}