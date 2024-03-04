package pw.feed.postwriter.util;

import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.user.User;

public final class TestUtil {

    private TestUtil() {}

    public static User createUser(String name) {
        return User.builder()
                .username(name)
                .email(name + "@example.com")
                .build();
    }

    public static Group createGroup(String name) {
        return Group.builder()
                .name(name)
                .description("Description for " + name)
                .build();
    }

    public static Post createPost(String title, User user, Group group) {
        return Post.builder()
                .title(title)
                .description("Desc for " + title)
                .user(user)
                .group(group)
                .build();
    }
}
