package pw.feed.postwriter.model.follow;

import jakarta.persistence.*;
import lombok.*;
import pw.feed.postwriter.model.user.User;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "user_follow")
public class UserFollow {
    @EmbeddedId
    private UserFollowId id;

    @ManyToOne
    @MapsId("userId")
    private User user;
    @ManyToOne
    @MapsId("followUserId")
    private User followUser;

}
