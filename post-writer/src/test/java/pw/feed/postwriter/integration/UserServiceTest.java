package pw.feed.postwriter.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pw.feed.postwriter.model.follow.UserFollowRepository;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.dto.UserProfileDTO;
import pw.feed.postwriter.model.user.UserRepository;
import pw.feed.postwriter.service.FollowService;
import pw.feed.postwriter.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static pw.feed.postwriter.util.TestUtil.createUser;

@SpringBootTest
public class UserServiceTest extends AbstractPostgresContainer {

    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Test
    void shouldBeValidUserProfile() {
        // Given
        User user1 = userRepository.save(createUser("user1"));
        User user2 = userRepository.save(createUser("user2"));
        User user3 = userRepository.save(createUser("user3"));
        followService.followUser(user1.getId(), user2.getId());
        followService.followUser(user1.getId(), user3.getId());
        followService.followUser(user3.getId(), user1.getId());

        // When
        UserProfileDTO userProfile = userService.getUserProfile(user1.getId());

        // Then
        assertAll(
                () -> assertEquals("user1", userProfile.getUsername(), "Username should be valid"),
                () -> assertEquals(2, userProfile.getFollowingUsernames().size(), "FollowingUsernames size should be valid"),
                () -> assertTrue(userProfile.getFollowingUsernames().contains("user2"), "FollowingUsernames should contain user2"),
                () -> assertEquals(1, userProfile.getFollowerUsernames().size(), "FollowerUsernames size should be valid ")
        );

    }

}