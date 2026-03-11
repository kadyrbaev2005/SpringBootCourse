package spring.sis6.notification.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class Notification {
    @Id
    private UUID id;
    private UUID postId;
    private String userId;
    private String message;
    private LocalDateTime createdAt;
}