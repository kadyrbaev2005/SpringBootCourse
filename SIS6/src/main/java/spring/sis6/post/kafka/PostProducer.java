package spring.sis6.post.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import spring.sis6.event.PostCreatedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostProducer {

    private final KafkaTemplate<String, PostCreatedEvent> kafkaTemplate;

    public void publishPost(PostCreatedEvent event) {
        log.info("Publishing post event to Kafka: {}", event.getPostId());
        kafkaTemplate.send("posts", event.getPostId(), event);
    }
}