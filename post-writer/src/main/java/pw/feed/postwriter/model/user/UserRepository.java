package pw.feed.postwriter.model.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    //    @EntityGraph(attributePaths = {"userFollows", "followerUsers"})
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userFollows LEFT JOIN FETCH u.followerUsers WHERE u.id = :id")
    Optional<User> findWithFollowsIdsById(Integer id);

    @EntityGraph(attributePaths = {"userFollows", "followerUsers", "groups"})
    Optional<User> findWithFollowsNGroupIdsById(Integer id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);


}
