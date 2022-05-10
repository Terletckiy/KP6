package by.TerleckiyKrepets.project.repository;

import by.TerleckiyKrepets.project.entity.Subscriber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends BaseRepository<Subscriber> {
    Page<Subscriber> findSubscriberBySubscriptionId(Long id, Pageable pageable);
    List<Subscriber> findSubscriberBySubscriptionId(Long id);
    List<Subscriber> findSubscribersByUserId(Long id);
}
