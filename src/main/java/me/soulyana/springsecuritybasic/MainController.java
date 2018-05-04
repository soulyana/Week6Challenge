package me.soulyana.springsecuritybasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("registeredusers", users.count());
        model.addAttribute("privateEvent", events.countAllByPrivateEvent(true));
        model.addAttribute("publicEvent", events.countAllByPrivateEvent(false));
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
