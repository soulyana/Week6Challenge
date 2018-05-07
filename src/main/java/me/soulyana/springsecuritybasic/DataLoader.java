package me.soulyana.springsecuritybasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    AppUserRepository userRepository;

    @Autowired
    AppRoleRepository roleRepository;

    @Autowired
    EventRepository eventRepository;

    public void run(String... strings) throws Exception {
        System.out.println("Loading data...");

        roleRepository.save(new AppRole("USER"));
        roleRepository.save(new AppRole("ADMIN"));


        AppRole adminRole = roleRepository.findByName("ADMIN");
        AppRole userRole = roleRepository.findByName("USER");

        AppUser user = new AppUser("user1", "password1");
        user.setRoles( new HashSet<>(Arrays.asList(adminRole)));
        userRepository.save(user);
        System.out.println("Saved user. Check in database.");

        user = new AppUser("Esaite", "littlesis", new HashSet<>(Arrays.asList(adminRole)));
        userRepository.save(user);
        System.out.println("Saved user. Check in database.");

        user = new AppUser("Sihin", "mommydearest", new HashSet<>(Arrays.asList(adminRole)));
        userRepository.save(user);
        System.out.println("Saved user. Check in database.");

        user = new AppUser("Lakew", "daddydearest", new HashSet<>(Arrays.asList(adminRole)));
        userRepository.save(user);
        System.out.println("Saved user. Check in database.");

        user = new AppUser("Soul", "myself1", new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
        System.out.println("Saved user. Check in database.");

        Event anEvent = new Event();
        anEvent.setName("Poor People's Campaign");
        anEvent.setStartDate("05/06/18");
        anEvent.setEndDate("06/09/18");
        anEvent.setPrivateEvent(false);
        eventRepository.save(anEvent);

        anEvent = new Event();
        anEvent.setName("Danny's 30th Birthday Party");
        anEvent.setStartDate("07/07/18");
        anEvent.setEndDate("07/08/18");
        anEvent.setPrivateEvent(true);
        eventRepository.save(anEvent);

    }
}
