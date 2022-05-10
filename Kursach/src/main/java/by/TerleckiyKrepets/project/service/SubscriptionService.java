package by.TerleckiyKrepets.project.service;

import by.TerleckiyKrepets.project.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionService extends BaseService<Subscription>{
    Page<Subscription> findAll(Pageable pageable);
    void saveProject(Subscription subscription);
    void changeInfo(Long id);
}
