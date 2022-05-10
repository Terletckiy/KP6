package by.TerleckiyKrepets.project.service;

import by.TerleckiyKrepets.project.entity.Subscriber;
import by.TerleckiyKrepets.project.entity.Subscription;
import by.TerleckiyKrepets.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubscriberService extends BaseService<Subscriber>{
    Page<Subscriber> findProjectEmployeeByProjectId(Long id, Pageable pageable);
    List<Subscriber> findProjectEmployeeByProjectId(Long id);
    List<User> findNotProjectUsers(Long id);
    Optional<Subscription> findProjectById(Long id);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    void addEmployee(Subscriber projectEmployee);
    void deleteEmployee(Long id);
    List<Subscriber> findUserSubscribers(Long id);

}
