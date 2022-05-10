package by.TerleckiyKrepets.project.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String employeeRole;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Subscriber() {
    }

    public Subscriber(Long id, String employeeRole, Subscription subscription, User user) {
        this.id = id;
        this.employeeRole = employeeRole;
        this.subscription = subscription;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
