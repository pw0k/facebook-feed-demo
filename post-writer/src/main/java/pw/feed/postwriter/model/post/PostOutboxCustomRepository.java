package pw.feed.postwriter.model.post;

import java.util.List;

public interface PostOutboxCustomRepository {
    List<PostOutbox> findLimitedPendingPostOutboxesForUpdate(int limit);

}
