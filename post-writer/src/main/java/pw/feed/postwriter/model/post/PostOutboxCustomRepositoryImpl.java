package pw.feed.postwriter.model.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class PostOutboxCustomRepositoryImpl implements PostOutboxCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PostOutbox> findLimitedPendingPostOutboxesForUpdate(int limit) {
        String sql = "SELECT * FROM post_outbox WHERE post_outbox_status = 'PENDING' ORDER BY created_at ASC FOR UPDATE SKIP LOCKED";
        return entityManager.createNativeQuery(sql, PostOutbox.class)
                .setMaxResults(limit)
                .getResultList();
//        return entityManager.createQuery(
//                        "SELECT p FROM PostOutbox p WHERE p.postOutboxStatus = 'PENDING' ORDER BY p.createdAt ASC", PostOutbox.class)
//                .setMaxResults(limit)
//                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
//                .getResultList();
    }
}
