package spring.sis6.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Request to create a new post")
public class CreatePostRequest {

    @NotBlank(message = "userId is required")
    @Schema(description = "ID of the user", example = "u1")
    private String userId;

    @NotBlank(message = "Content is required")
    @Size(max = 280, message = "Content must not exceed 280 characters")
    @Schema(description = "Post content, max 280 characters", example = "Hello Kafka world!")
    private String content;

    @Schema(description = "List of hashtags", example = "[\"kafka\", \"spring\"]")
    private List<String> hashtags;
}