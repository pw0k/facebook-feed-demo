package pw.feed.postwriter.model.post;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_outbox")
public class PostOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "post_ou_seq")
    @SequenceGenerator(name = "post_ou_seq",
            sequenceName = "post_outbox_id_seq",
            allocationSize = 10)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(nullable = false)
    private String username;

    private String groupname;

    @Column(name = "post_outbox_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostOutboxStatus postOutboxStatus;

    @Column(name = "post_outbox_event_type")
    @Enumerated(EnumType.STRING)
    private PostOutboxEventType postOutboxEventType;
}





