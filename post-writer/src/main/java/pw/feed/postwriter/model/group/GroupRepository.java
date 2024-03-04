package pw.feed.postwriter.model.group;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer>{

    @EntityGraph(attributePaths = {"followers"})
    Optional<Group> findWithFollowersById(Integer id);
    boolean existsByName(String name);
}
