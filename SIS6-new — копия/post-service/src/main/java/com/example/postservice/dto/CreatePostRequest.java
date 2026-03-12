package com.example.postservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "Request object for creating a new post")
public class CreatePostRequest {

    @NotBlank(message = "User ID is required")
    @Schema(description = "ID of the user creating the post", example = "user-42", required = true)
    private String userId;

    @NotBlank(message = "Content is required")
    @Size(max = 280, message = "Content must not exceed 280 characters")
    @Schema(description = "Post content, max 280 characters", example = "Hello Kafka!", required = true)
    private String content;

    @Schema(description = "List of hashtags without #", example = "[\"kafka\", \"spring\"]")
    private List<String> hashtags;

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getHashtags() { return hashtags; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }
}