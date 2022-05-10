package by.TerleckiyKrepets.project.service.impl;

import by.TerleckiyKrepets.project.repository.SubscriptionRepository;
import by.TerleckiyKrepets.project.service.SubscriptionService;
import by.TerleckiyKrepets.project.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository projectRepository;

    public SubscriptionServiceImpl(SubscriptionRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return projectRepository.findSubscriptionById(id);
    }

    @Override
    public void saveProject(Subscription project) {
        projectRepository.save(project);
    }

    @Override
    public void changeInfo(Long id) {

    }
}
