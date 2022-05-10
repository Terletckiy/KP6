package by.TerleckiyKrepets.project.service.impl;

import by.TerleckiyKrepets.project.entity.Subscriber;
import by.TerleckiyKrepets.project.repository.SubscriberRepository;
import by.TerleckiyKrepets.project.service.SubscriberService;
import by.TerleckiyKrepets.project.entity.Subscription;
import by.TerleckiyKrepets.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository projectEmployeeRepository;
    private final SubscriptionServiceImpl projectService;
    private final UserServiceImpl userService;

    public SubscriberServiceImpl(SubscriberRepository projectEmployeeRepository, SubscriptionServiceImpl projectService, UserServiceImpl userService) {
        this.projectEmployeeRepository = projectEmployeeRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public List<User> findNotProjectUsers(Long id) {
        List<User> userList = userService.findAllUsers();
        List<User> notProjectUsers = userService.findAllUsers();
        Optional<Subscription> project = projectService.findById(id);
        if(project.isPresent()) {
            for (User user : userList) {
                for (Subscriber employee: project.get().getSubscribers()) {
                    if(employee.getUser().getId() == user.getId()){
                        notProjectUsers.remove(user);
                    }
                }
            }
        }
        return notProjectUsers;
    }

    @Override
    public Page<Subscriber> findProjectEmployeeByProjectId(Long id, Pageable pageable) {
        return projectEmployeeRepository.findSubscriberBySubscriptionId(id, pageable);
    }

    @Override
    public List<Subscriber> findProjectEmployeeByProjectId(Long id) {
        return projectEmployeeRepository.findSubscriberBySubscriptionId(id);
    }

    @Override
    public Optional<Subscription> findProjectById(Long id) {
        return projectService.findById(id);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userService.findById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    @Override
    public Optional<Subscriber> findById(Long id) {
        return projectEmployeeRepository.findById(id);
    }

    @Override
    public void addEmployee(Subscriber subscriber) {
        List<Subscriber> employees = findProjectEmployeeByProjectId(subscriber.getSubscription().getId());
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date); //устанавливаем дату, с которой будет производить операции
        instance.add(Calendar.DAY_OF_MONTH, 30);// прибавляем 3 дня к установленной дате
        subscriber.setDate(instance.getTime());

        if(employees.isEmpty()){
            subscriber.setEmployeeRole("Leader");
        }else{
            subscriber.setEmployeeRole("Developer");
        }
        projectEmployeeRepository.save(subscriber);
    }

    @Override
    public void deleteEmployee(Long id) {
        Optional<Subscriber> employee = findById(id);
        if(employee.isPresent()){
            List<Subscriber> employees = findProjectEmployeeByProjectId(employee.get().getSubscription().getId());
            if(employees.size() > 1 && employees.get(0).equals(employee.get())) {
                employees.get(1).setEmployeeRole("Leader");
                projectEmployeeRepository.save(employees.get(1));
            }
            employee.ifPresent(projectEmployeeRepository::delete);
        }
    }

    @Override
    public List<Subscriber> findUserSubscribers(Long id) {
        return projectEmployeeRepository.findSubscribersByUserId(id);
    }
}
