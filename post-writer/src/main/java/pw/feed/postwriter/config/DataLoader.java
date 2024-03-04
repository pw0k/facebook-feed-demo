package pw.feed.postwriter.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.post.PostRepository;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.service.FollowService;
import pw.feed.postwriter.service.GroupService;
import pw.feed.postwriter.service.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private final PostRepository postRepository;
    private final FollowService followService;
    private final GroupService groupService;
    private final UserService userService;

    @PostConstruct
    private void loadData() {
        if (!userService.existsByEmail("Schwifty@universe.com")) {
            User user1 = createAndSaveUser("pickle", "Schwifty@universe.com");
            User user2 = createAndSaveUser("morty", "Geez@universe.com");
            Group group1 = createAndSaveGroup("Pickle team", "Pickle Pickle");

            createAndSavePost("Wubba Lubba", "Dub Dub", user1, null);
            createAndSavePost("I' 'm Mr. Meeseeks", "Look at me!", user2, group1);

            followService.followUser(user2.getId(), user1.getId());
            groupService.followToGroup(user1.getId(), group1.getId());
            groupService.followToGroup(user2.getId(), group1.getId());

            log.warn("test data created");
        }
    }

    private User createAndSaveUser(String username, String email) {
        return userService.save(User.builder()
                .username(username)
                .email(email)
                .build());
    }

    private Group createAndSaveGroup(String name, String description) {
        return groupService.save(Group.builder()
                .name(name)
                .description(description)
                .build());
    }

    private Post createAndSavePost(String title, String description, User user, Group group) {
        return postRepository.save(Post.builder()
                .title(title)
                .description(description)
                .user(user)
                .group(group)
                .build());
    }
}
