package com.example.feedservice.controller;

import com.example.feedservice.dto.FeedItemResponse;
import com.example.feedservice.entity.FeedItem;
import com.example.feedservice.repository.FeedItemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feed")
@Tag(name = "Feed Controller", description = "Endpoints for retrieving user feeds")
public class FeedController {

    private final FeedItemRepository feedItemRepository;

    public FeedController(FeedItemRepository feedItemRepository) {
        this.feedItemRepository = feedItemRepository;
    }

    @GetMapping
    @Operation(summary = "Get user feed", description = "Retrieves all feed items for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feed retrieved successfully",
                    content = @Content(schema = @Schema(implementation = FeedItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<FeedItemResponse>> getUserFeed(
            @Parameter(description = "ID of the user", example = "user-42", required = true)
            @RequestParam String userId) {

        List<FeedItem> feedItems = feedItemRepository.findByUserIdOrderByCreatedAtDesc(userId);

        List<FeedItemResponse> responses = feedItems.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    private FeedItemResponse convertToResponse(FeedItem item) {
        FeedItemResponse response = new FeedItemResponse();
        response.setId(item.getId());
        response.setPostId(item.getPostId());
        response.setUserId(item.getUserId());
        response.setContent(item.getContent());
        response.setHashtags(item.getHashtags() != null ?
                List.of(item.getHashtags().split(",")) : null);
        response.setCreatedAt(item.getCreatedAt());
        response.setReceivedAt(item.getReceivedAt());
        return response;
    }
}