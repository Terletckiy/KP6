package by.TerleckiyKrepets.project.controller;

import by.TerleckiyKrepets.project.service.SubscriptionService;
import by.TerleckiyKrepets.project.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {

    private static final int DEFAULT_SIZE = 10;
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public String subscriptions(@RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
        Page<Subscription> list = subscriptionService.findAll(pageable);
        model.addAttribute("subscriptions", list.getContent());
        model.addAttribute("page", list.getNumber());
        model.addAttribute("pageCount", list.getTotalPages()-1);
        return RedirectPage.SUBSCRIPTION_LIST;
    }

    @GetMapping("/{id}")
    public String subscription(@PathVariable("id") Long id, Model model){
        Optional<Subscription> subscription = subscriptionService.findById(id);
        String rotation = RedirectPage.SUBSCRIPTION_LIST;
        if(subscription.isPresent()){
            rotation = RedirectPage.SUBSCRIPTION_INFO;

            model.addAttribute("subscription", subscription.get());
        }
        return rotation;
    }

    @GetMapping("/new")
    public String addSubscription(@ModelAttribute("subscription") Subscription subscription){
       return RedirectPage.NEW_SUBSCRIPTION;
    }

    @PostMapping
    public String saveSubscription(@Valid Subscription subscription, BindingResult bindingResult){
        String rotation = RedirectPage.NEW_SUBSCRIPTION;
        if(!bindingResult.hasErrors()){
            rotation = Redirect.SUBSCRIPTION_LIST;
            subscriptionService.saveProject(subscription);
        }
        return rotation;
    }
}
