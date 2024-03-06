package pw.feed.postwriter.model.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostOutboxRepository extends JpaRepository<PostOutbox, Long> {

    List<PostOutbox> findAllByPostOutboxEventType(PostOutboxEventType eventType);


}
