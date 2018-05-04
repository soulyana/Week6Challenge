package me.soulyana.springsecuritybasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    AppUserRepository users;

    @Autowired
    AppRoleRepository roles;

    @Autowired
    EventRepository events;

    @RequestMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("events", events.findAll());
        return "index";
    }

    @RequestMapping("/addevent")
    public String addEvent(Model model) {
        model.addAttribute("anEvent", new Event());
        return "eventform";
    }

    @PostMapping("/addevent")
    public String addEvent(@Valid @ModelAttribute("anEvent") Event event, BindingResult result) {
        if(result.hasErrors()) {
            return "eventform";
        } else {
            events.save(event);
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerNewUser(Model model) {
        model.addAttribute("aUser", new AppUser());
        return "registrationform";
    }

    @PostMapping("/register")
    public String saveRegisteredUser(@Valid @ModelAttribute("aUser") AppUser user, BindingResult result) {
        if(result.hasErrors()) {
            return "registrationform";
        } else {
            users.save(user);
        }
        return "redirect:/";
    }

    @GetMapping("/viewevents")
    public String viewEvents(Authentication auth, Model model) {
        if(auth==null) {
            //User is not authenticated
            model.addAttribute("events", events.findAllByPrivateEvent(false));
            model.addAttribute("title", "Public Events");
        } else {
            model.addAttribute("events", events.findAll());
            model.addAttribute("title", "All Events");
        }
        return "listevents";
    }

    @GetMapping("/showprivateevents")
    public String showPrivateEvents(Model model)
    {
        model.addAttribute("events",events.findAllByPrivateEvent(true));
        model.addAttribute("title", "Private Events");
        return "listrooms";
    }

    @GetMapping("/changeeventstatus/{id}")
    public String changeEventStatus(@PathVariable("id") long id) {
        Event thisEvent = events.findById(id).get();
        thisEvent.setPrivateEvent(!thisEvent.isPrivateEvent());
        events.save(thisEvent);
        return "redirect:/viewevents";
    }

    @PostConstruct
    public void addUsers() {
        AppUser aUser = new AppUser();
        aUser.setUsername("user1");
        aUser.setPassword("password1");
        users.save(aUser);

        AppRole aRole = new AppRole();
        aRole.setName("ADMIN");
        roles.save(aRole);

        Event anEvent = new Event();
        anEvent.setName("Poor People's Campaign");
        anEvent.setStartDate("05/06/18");
        anEvent.setEndDate("06/09/18");
        anEvent.setPrivateEvent(false);
        events.save(anEvent);

        anEvent = new Event();
        anEvent.setName("Danny's 30th Birthday Party");
        anEvent.setStartDate("07/07/18");
        anEvent.setEndDate("07/08/18");
        anEvent.setPrivateEvent(true);
        events.save(anEvent);
    }



}
