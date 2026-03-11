package spring.sis6.notification.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import spring.sis6.event.PostCreatedEvent;
import spring.sis6.notification.entity.Notification;
import spring.sis6.notification.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "posts", groupId = "notification-group")
    public void consume(PostCreatedEvent event) {
        log.info("🔔 Sending push notification to followers of user {} — new post {}",
                event.getUserId(), event.getPostId());

        Notification notification = new Notification();
        notification.setId(UUID.randomUUID());
        notification.setPostId(UUID.fromString(event.getPostId()));
        notification.setUserId(event.getUserId());
        notification.setMessage("New post from " + event.getUserId()
                + ": " + event.getContent());
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}