package pw.feed.postwriter.model.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
            SELECT p
            FROM Post p
            WHERE p.user.id IN :userIds
            OR p.group.id IN :groupIds
            ORDER BY p.createdAt DESC""")
    Set<Post> findPostsByFollowedUsersOrGroups(@Param("userIds") List<Integer> userIds, @Param("groupIds") List<Integer> groupIds);
}
