package spring.sis6.feed.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "feed_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class FeedItem {
    @Id
    private UUID id;
    private UUID postId;
    private String userId;
    private String content;
    private String hashtags;
    private LocalDateTime createdAt;
}