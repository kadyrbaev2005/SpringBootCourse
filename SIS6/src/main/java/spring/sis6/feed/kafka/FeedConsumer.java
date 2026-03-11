package spring.sis6.feed.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import spring.sis6.event.PostCreatedEvent;
import spring.sis6.feed.entity.FeedItem;
import spring.sis6.feed.repository.FeedItemRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FeedConsumer {

    private final FeedItemRepository feedItemRepository;

    @KafkaListener(topics = "posts", groupId = "feed-group")
    public void consume(PostCreatedEvent event) {

        if (event.getContent() == null || event.getContent().isBlank()) {
            log.warn("⚠️ Skipping event {} — content is empty", event.getPostId());
            return;
        }

        log.info("📰 Adding post {} by user {} to follower feeds — '{}'",
                event.getPostId(), event.getUserId(), event.getContent());

        FeedItem feedItem = new FeedItem();
        feedItem.setId(UUID.randomUUID());
        feedItem.setPostId(UUID.fromString(event.getPostId()));
        feedItem.setUserId(event.getUserId());
        feedItem.setContent(event.getContent());
        feedItem.setHashtags(event.getHashtags() != null ?
                String.join(",", event.getHashtags()) : "");
        feedItem.setCreatedAt(LocalDateTime.now());

        feedItemRepository.save(feedItem);
    }
}