package pw.feed.postwriter.model.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserFollowId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "follow_user_id")
    private Integer followUserId;
}
