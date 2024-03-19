package pw.feed.postwriter.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pw.feed.postwriter.model.post.*;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;
import pw.feed.postwriter.service.PostService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pw.feed.postwriter.util.Faker.createPost;
import static pw.feed.postwriter.util.Faker.createUser;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class PostServiceIntegrationTest extends AbstractPostgresContainer {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostOutboxRepository postOutboxRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void whenCreatingPost_thenAllSavedCorrect() {
        // Given
        User user = createUser("username");
        Post post = createPost("Initial Title", user, null);

        // When
        userRepository.save(user);
        Post savedPost = postService.saveWithOutbox(post);
        List<PostOutbox> postOutboxList =
                postOutboxRepository.findLimitedPendingPostOutboxesForUpdate(10);

        // Then
        assertAll(
                () -> assertEquals(1, postOutboxList.size(), "size list must be the same"),
                () -> assertNotNull(savedPost, "savedPost must be not null"),
                () -> assertEquals(post.getTitle(), savedPost.getTitle(), "savedPost title must be the same"),
                () -> assertEquals(post.getUser().getUsername(), savedPost.getUser().getUsername(),
                        "username  must be the same"),
                () -> assertEquals(post.getTitle(), postOutboxList.get(0).getTitle(), "postOutbox title must be the same"),
                () -> assertEquals(PostOutboxEventType.POST_CREATED, postOutboxList.get(0).getPostOutboxEventType(),
                        "PostOutboxEventType  must be the same"),
                () -> assertEquals(PostOutboxStatus.PENDING, postOutboxList.get(0).getPostOutboxStatus(),
                        "PostOutboxStatus  must be the same")
        );
    }

    @Test
    void whenUpdatingPost_thenAllSavedCorrect() {
        // Given
        User user = createUser("username");
        Post post = createPost("Initial Title", user, null);

        // When
        userRepository.save(user);
        Post savedPost = postService.saveWithOutbox(post);
        savedPost.setTitle("Updated title");
        Post updatedPost =postService.updateWithOutbox(savedPost.getId(), savedPost);
        List<PostOutbox> postOutboxList =
                postOutboxRepository.findLimitedPendingPostOutboxesForUpdate(10);


        // Then
        assertAll(
                () -> assertNotNull(updatedPost, "updatedPost must be not null"),
                () -> assertEquals("Updated title", updatedPost.getTitle(), "updatedPost title must be the same"),
                () -> assertEquals(updatedPost.getUser().getUsername(), savedPost.getUser().getUsername(),
                        "username  must be the same"),
                () -> assertEquals(updatedPost.getTitle(), postOutboxList.get(0).getTitle(), "title must be the same"),
                () -> assertEquals(PostOutboxEventType.POST_UPDATED, postOutboxList.get(0).getPostOutboxEventType(),
                        "PostOutboxEventType  must be the same"),
                () -> assertEquals(PostOutboxStatus.PENDING, postOutboxList.get(0).getPostOutboxStatus(),
                        "PostOutboxStatus  must be the same")
        );
    }
}
