package by.TerleckiyKrepets.project.repository;

import by.TerleckiyKrepets.project.entity.Subscription;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends BaseRepository<Subscription> {
    Optional<Subscription> findSubscriptionById(Long id);
}
