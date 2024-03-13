package pw.feed.postwriter.model.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOutboxRepository extends JpaRepository<PostOutbox, Long>, PostOutboxCustomRepository {

}
