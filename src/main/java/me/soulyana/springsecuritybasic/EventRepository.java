package me.soulyana.springsecuritybasic;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

    //Count the number of public(false)/private(true) events
    int countAllByPrivateEvent(boolean privateEvent);

    //Get all public (false) events and private(true) events
    Iterable<Event> findAllByPrivateEvent(boolean privateEvent);
}
