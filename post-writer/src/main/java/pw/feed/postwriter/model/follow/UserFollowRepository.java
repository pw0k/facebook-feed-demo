package pw.feed.postwriter.model.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowId> {

}
