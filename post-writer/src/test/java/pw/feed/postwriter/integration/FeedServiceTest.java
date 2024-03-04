package pw.feed.postwriter.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.group.GroupRepository;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.post.PostRepository;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;
import pw.feed.postwriter.service.FeedService;
import pw.feed.postwriter.service.FollowService;
import pw.feed.postwriter.service.GroupService;
import pw.feed.postwriter.service.dto.PostRecord;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pw.feed.postwriter.util.TestUtil.*;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
class FeedServiceTest extends AbstractPostgresContainer {

    @Autowired private FeedService feedService;
    @Autowired private FollowService followService;
    @Autowired private GroupService groupService;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;

    @Test
    //todo check sql joins and analize query
    public void shouldHasPostsFromFollowedUsersAndGroupsInFeed() {
        // Given
        User user1 = createUser("user1");
        User user2 = createUser("user2");
        User user3 = createUser("user3");
        Group group1 = createGroup("group1");
        Post postByUser2 = createPost("post1", user2, null);
        Post postByGroup1 = createPost("post2", user3, group1);

        userRepository.saveAll(List.of(user1, user2, user3));
        groupRepository.save(group1);
        followService.followUser(user1.getId(), user2.getId());
        groupService.followToGroup(user1.getId(), group1.getId());
        postRepository.saveAll(List.of(postByUser2, postByGroup1));

        // When
        List<PostRecord> feed = feedService.getFeedForUser(user1.getUsername());

        // Then
        assertAll(
                () -> assertTrue(feed.stream()
                                .anyMatch(post -> post.title().equals("post1")),
                        "Feed should contain posts from followed users"),
                () -> assertTrue(feed.stream()
                                .anyMatch(post -> post.title().equals("post2")),
                        "Feed should contain posts from groups the user is a member of")
        );
    }
}