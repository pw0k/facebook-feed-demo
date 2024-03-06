package pw.feed.postwriter.service.converter;

import org.springframework.stereotype.Component;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.post.PostOutbox;
import pw.feed.postwriter.model.post.PostOutboxEventType;
import pw.feed.postwriter.model.post.PostOutboxStatus;

@Component
public class PostOutboxConverter {

    public PostOutbox convert(Post post, PostOutboxEventType type) {
        return PostOutbox.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .username(post.getUser().getUsername())
                .groupname(post.getGroup() != null ? post.getGroup().getName() : null)
                .postOutboxStatus(PostOutboxStatus.PENDING)
                .postOutboxEventType(type)
                .build();
    }
}
