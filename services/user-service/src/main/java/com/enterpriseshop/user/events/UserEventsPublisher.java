package com.enterpriseshop.user.events;

import com.enterpriseshop.events.Topics;
import com.enterpriseshop.events.UserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventsPublisher {

    private final KafkaTemplate<String, Object> kafka;

    public UserEventsPublisher(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    public void userRegistered(UserRegisteredEvent event) {
        // key by userId so a user's events keep ordering within a partition
        kafka.send(Topics.USER_REGISTERED, event.userId(), event);
    }
}
