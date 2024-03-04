package pw.feed.postwriter.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;
import pw.feed.postwriter.service.FollowService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static pw.feed.postwriter.util.TestUtil.createUser;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class FollowServiceTest extends AbstractPostgresContainer {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowService followService;

    @Test
    void userShouldFollowingToUser() {
        // Given
        User user1 = userRepository.save(createUser("user1"));
        User user2 = userRepository.save(createUser("user2"));
        User user3 = userRepository.save(createUser("user3"));

        // When
        followService.followUser(user1.getId(), user2.getId());
        followService.followUser(user3.getId(), user1.getId());
        User fetchedUser1 = userRepository.findWithFollowsIdsById(user1.getId())
                .orElseThrow(() -> new AssertionError("fetchedUser1 should exist"));
        User fetchedUser2 = userRepository.findWithFollowsIdsById(user2.getId())
                .orElseThrow(() -> new AssertionError("fetchedUser2 should exist"));

        // Then
        assertAll(
                () -> assertTrue(fetchedUser1.getUserFollows().stream()
                        .anyMatch(f -> Objects.equals(f.getId().getFollowUserId(), user2.getId())),
                        "UserFollows should be same"),
                () -> assertFalse(fetchedUser1.getUserFollows().stream()
                        .anyMatch(f -> Objects.equals(f.getId().getFollowUserId(), user3.getId())),
                        "UserFollows shouldn't exist"),
                () -> assertTrue(fetchedUser2.getFollowerUsers().stream()
                        .anyMatch(f -> Objects.equals(f.getId().getUserId(), user1.getId())),
                        "FollowUser should be same")
        );
    }

    @Test
    void userShouldUnfollowingFromUser() {
        // Given
        User user4 = userRepository.save(createUser("user4"));
        User user5 = userRepository.save(createUser("user5"));
        followService.followUser(user4.getId(), user5.getId());

        // When
        followService.unfollowUser(user4.getId(), user5.getId());
        User fetchedUser1 = userRepository.findWithFollowsIdsById(user4.getId())
                .orElseThrow(() -> new AssertionError("fetchedUser1 should exist"));
        User fetchedUser2 = userRepository.findWithFollowsIdsById(user5.getId())
                .orElseThrow(() -> new AssertionError("fetchedUser2 should exist"));

        // Then
        assertAll(
                () -> assertTrue(fetchedUser1.getUserFollows().isEmpty(), "UserFollows should be empty"),
                () -> assertTrue(fetchedUser2.getFollowerUsers().isEmpty(), "FollowerUsers should be empty")
        );

    }

}