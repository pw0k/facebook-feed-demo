package pw.feed.postwriter.util;

import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.post.PostOutbox;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.service.dto.PostRecord;

import java.time.Instant;

public class Faker {

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

    public static PostRecord createPostRecord(String title, String desctiption){
        return new PostRecord(title, desctiption, Instant.now());
    }

    public static PostOutbox createPostOutbox(String title) {
        return createPostOutbox(title, null, null);
    }

    public static PostOutbox createPostOutbox(String title, User user, Group group) {
        return PostOutbox.builder()
                .title(title)
                .description("Desc for " + title)
                .username(user == null ? null : user.getUsername())
                .groupname(group == null ? null : group.getName())
                .build();
    }
}
