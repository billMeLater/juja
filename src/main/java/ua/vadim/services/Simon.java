package ua.vadim.services;

import org.springframework.stereotype.Component;

@Component
public class Simon implements MessageService {
    public String sentMessage(String message) {
        return "Simon says: " + message;
    }
}
