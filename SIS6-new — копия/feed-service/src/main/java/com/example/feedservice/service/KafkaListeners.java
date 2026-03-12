package com.example.feedservice.service;

import com.example.feedservice.entity.FeedItem;
import com.example.feedservice.entity.Notification;
import com.example.feedservice.event.PostCreatedEvent;
import com.example.feedservice.repository.FeedItemRepository;
import com.example.feedservice.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class KafkaListeners {

    private static final Logger log = LoggerFactory.getLogger(KafkaListeners.class);

    private final FeedItemRepository feedItemRepository;
    private final NotificationRepository notificationRepository;

    public KafkaListeners(FeedItemRepository feedItemRepository,
                          NotificationRepository notificationRepository) {
        this.feedItemRepository = feedItemRepository;
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = "posts", groupId = "feed-group")
    @Transactional
    public void consumeForFeed(PostCreatedEvent event) {
        log.info("📰 Received post for feed: {}", event.getPostId());

        // Task 4: Error handling - skip if content is blank or null
        if (event.getContent() == null || event.getContent().trim().isEmpty()) {
            log.warn("⚠️ Skipping event {} — content is empty", event.getPostId());
            return;
        }

        // Save to feed_items table
        FeedItem feedItem = new FeedItem();
        feedItem.setPostId(event.getPostId());
        feedItem.setUserId(event.getUserId());
        feedItem.setContent(event.getContent());
        feedItem.setHashtags(event.getHashtags() != null ?
                String.join(",", event.getHashtags()) : null);
        feedItem.setCreatedAt(event.getTimestamp());
        feedItem.setReceivedAt(LocalDateTime.now());

        feedItemRepository.save(feedItem);

        log.info("📰 Adding post {} by user {} to follower feeds — '{}'",
                event.getPostId(), event.getUserId(), event.getContent());
    }

    @KafkaListener(topics = "posts", groupId = "notification-group")
    @Transactional
    public void consumeForNotification(PostCreatedEvent event) {
        log.info("🔔 Received post for notification: {}", event.getPostId());

        // Save to notifications table
        Notification notification = new Notification();
        notification.setPostId(event.getPostId());
        notification.setUserId(event.getUserId());
        notification.setContent(event.getContent());
        notification.setNotificationType("NEW_POST");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        notificationRepository.save(notification);

        log.info("🔔 Sending push notification to followers of user {} — new post {}",
                event.getUserId(), event.getPostId());
    }
}