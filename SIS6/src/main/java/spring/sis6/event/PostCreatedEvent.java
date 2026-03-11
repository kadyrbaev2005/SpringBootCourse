package spring.sis6.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreatedEvent {
    private String postId;
    private String userId;
    private String content;
    private List<String> hashtags;
    private LocalDateTime timestamp;
}