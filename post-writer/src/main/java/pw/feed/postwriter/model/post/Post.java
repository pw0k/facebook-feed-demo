package pw.feed.postwriter.model.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.user.User;


import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title must be set for a post")
    @Pattern(regexp = "^[a-zA-Z0-9 _\\-.!?']+$", message = "Title must not contain special characters")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Description must be set for a post")
    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "User must be set for a post")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;
}
