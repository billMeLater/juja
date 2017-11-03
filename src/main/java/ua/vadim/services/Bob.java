package ua.vadim.services;

import org.springframework.stereotype.Component;

//@Component
public class Bob implements MessageService {
    public String sentMessage(String message) {
        return "Bob says: " + message;
    }
}
