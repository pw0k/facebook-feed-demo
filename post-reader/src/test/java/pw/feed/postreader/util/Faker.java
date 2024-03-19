package pw.feed.postreader.util;

import pw.feed.postreader.model.PostOutbox;

public class Faker {
    public static PostOutbox createPostOutbox(String title) {
        return createPostOutbox(title, null, null);
    }

    public static PostOutbox createPostOutbox(String title, String username, String groupname) {
        return PostOutbox.builder()
                .title(title)
                .description("Desc for " + title)
                .username(username)
                .groupname(groupname)
                .build();
    }
}
