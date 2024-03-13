package pw.feed.postwriter.model.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class PostOutboxCustomRepositoryImpl implements PostOutboxCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    //todo blocking for using with several pods - FOR UPDATE SKIP LOCKED  ??
    @Override
    public List<PostOutbox> findLimitedPendingPostOutboxesForUpdate(int limit) {
        return entityManager.createQuery(
                        "SELECT p FROM PostOutbox p WHERE p.postOutboxStatus = 'PENDING' ORDER BY p.createdAt ASC", PostOutbox.class)
                .setMaxResults(limit)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getResultList();
    }
}
