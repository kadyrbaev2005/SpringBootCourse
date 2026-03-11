package spring.sis6.feed.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.sis6.feed.entity.FeedItem;
import spring.sis6.feed.repository.FeedItemRepository;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
@RequiredArgsConstructor
@Tag(name = "Feed API", description = "Operations for reading user feed")
public class FeedController {

    private final FeedItemRepository feedItemRepository;

    @Operation(summary = "Get feed for a user")
    @ApiResponse(responseCode = "200", description = "Feed items returned")
    @GetMapping
    public ResponseEntity<List<FeedItem>> getFeed(@RequestParam String userId) {
        return ResponseEntity.ok(feedItemRepository.findByUserId(userId));
    }
}