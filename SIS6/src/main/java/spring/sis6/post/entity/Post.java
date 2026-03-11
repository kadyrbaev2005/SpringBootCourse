package spring.sis6.post.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class Post {
    @Id
    private UUID id;
    private String userId;
    private String content;
    private String hashtags;
    private String status;
    private LocalDateTime createdAt;
}