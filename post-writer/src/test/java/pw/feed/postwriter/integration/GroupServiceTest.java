package pw.feed.postwriter.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.group.GroupRepository;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;
import pw.feed.postwriter.service.GroupService;


import static org.junit.jupiter.api.Assertions.*;
import static pw.feed.postwriter.util.TestUtil.createGroup;
import static pw.feed.postwriter.util.TestUtil.createUser;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
class GroupServiceTest extends AbstractPostgresContainer {

    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private GroupService groupService;

    @Test
    void userShouldFollowingToGroup() {
        // Given
        User user1 = userRepository.save(createUser("user1"));
        Group group1 = groupRepository.save(createGroup("group1"));


        // When
        groupService.followToGroup(user1.getId(), group1.getId());
        Group fetchedGroup = groupRepository.findWithFollowersById(group1.getId())
                .orElseThrow(() -> new AssertionError("fetchedGroup should exist"));

        // Then
        assertTrue(fetchedGroup.getFollowers().contains(user1), "User1 should be listed in the group's followers");
    }

    @Test
    void userShouldUnfollowingFromGroup() {
        // Given
        User user2 = userRepository.save(createUser("user2"));
        Group group2 = groupRepository.save(createGroup("group2"));
        groupService.followToGroup(user2.getId(), group2.getId());

        // When
        groupService.unfollowFromGroup(user2.getId(), group2.getId());
        Group fetchedGroup = groupRepository.findWithFollowersById(group2.getId())
                .orElseThrow(() -> new AssertionError("fetchedGroup should exist"));

        // Then
        assertFalse(fetchedGroup.getFollowers().contains(user2), "User should not be listed in the group's followers after unfollowing");

    }


}