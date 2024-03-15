package pw.feed.postreader.model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostOutbox {

    private Long id;
    private Long postId;
    private String title;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private String username;
    private String groupname;
    private PostOutboxStatus postOutboxStatus;
    private PostOutboxEventType postOutboxEventType;
}
