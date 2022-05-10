package by.TerleckiyKrepets.project.controller;

import by.TerleckiyKrepets.project.entity.Subscriber;
import by.TerleckiyKrepets.project.service.UserService;
import by.TerleckiyKrepets.project.util.Pagination;
import by.TerleckiyKrepets.project.entity.Subscription;
import by.TerleckiyKrepets.project.entity.User;
import by.TerleckiyKrepets.project.service.impl.SubscriberServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/subscriber")
public class SubscriberController {

    private static final int DEFAULT_SIZE = 10;
    private final SubscriberServiceImpl projectEmployeeService;
    private final UserService userService;

    public SubscriberController(SubscriberServiceImpl projectEmployeeService, UserService userService) {
        this.projectEmployeeService = projectEmployeeService;
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String userSubscriber(@PathVariable("id") Long id, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> accountCheck = userService.findUserByUsername(userDetails.getUsername());
        Optional<User> userCheck = userService.findById(id);
        String rotation = RedirectPage.USER_MENU;
        if(userCheck.isPresent() && accountCheck.isPresent()) {
            rotation = RedirectPage.USER_SUBSCRIPTION_LIST;
            model.addAttribute("role", accountCheck.get().getAccount().getRole());
            model.addAttribute("user", userCheck.get());
            model.addAttribute("subscribers", projectEmployeeService.findUserSubscribers(id));
        }
        return rotation;
    }



    @GetMapping("/add/{id}")
    public String addEmployee(@PathVariable("id") Long id,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                              Pagination<User> pagination, Model model){
        Optional<Subscription> project = projectEmployeeService.findProjectById(id);
        List<User> userList = projectEmployeeService.findNotProjectUsers(id);
        String rotation = Redirect.SUBSCRIPTION_LIST + "/" + id;
        if(project.isPresent()){
            model.addAttribute("subscription", project.get());
            model.addAttribute("users", pagination.pageSelect(page, userList));
            model.addAttribute("page", page);
            model.addAttribute("pageCount", pagination.pageCount(userList));
            rotation = RedirectPage.ADD_SUBSCRIBER;
        }
        return rotation;
    }

    @PostMapping("/add")
    public String add(@RequestParam("projectId") Long projectId, @RequestParam("userId") Long userId,
                      @Valid Subscriber projectEmployee, BindingResult bindingResult, Model model){
        Optional<Subscription> subscriptionCheck = projectEmployeeService.findProjectById(projectId);
        Optional<User> userCheck = projectEmployeeService.findUserById(userId);
        String rotation = RedirectPage.ADD_SUBSCRIBER;
        if(subscriptionCheck.isPresent() && userCheck.isPresent() && !bindingResult.hasErrors()) {
            projectEmployee.setSubscription(subscriptionCheck.get());
            projectEmployee.setUser(userCheck.get());
            projectEmployeeService.addEmployee(projectEmployee);
            model.addAttribute("id", subscriptionCheck.get().getId());
            model.addAttribute("page",  "0");
            rotation = Redirect.SUBSCRIPTION_LIST + "/" + subscriptionCheck.get().getId();
        }
        return rotation;
    }

    @GetMapping("/{id}")
    public String projectEmployee(@PathVariable("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){
        Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
        Page<Subscriber> subscribers = projectEmployeeService.findProjectEmployeeByProjectId(id, pageable);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = projectEmployeeService.findUserByUsername(userDetails.getUsername());
        Optional<Subscription> subscription = projectEmployeeService.findProjectById(id);
        String rotation = RedirectPage.SUBSCRIPTION_SUBSCRIBERS;
        if(subscription.isPresent() && user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("subscription", subscription.get());
            model.addAttribute("subscribers", subscribers.getContent());
            model.addAttribute("page", subscribers.getNumber());
            model.addAttribute("pageCount", subscribers.getTotalPages()-1);
        }
        return rotation;
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, @RequestParam("projectId") Long projectId, Model model) {
        Optional<Subscription> project = projectEmployeeService.findProjectById(projectId);
        if (project.isPresent()) {
            projectEmployeeService.deleteEmployee(id);
            model.addAttribute("id", projectId);
        }
        return Redirect.SUBSCRIPTION_LIST + "/" + projectId;
    }
}
