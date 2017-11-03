package ua.vadim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.vadim.services.MessageService;

@Component
public class Application {
    @Autowired
    @Qualifier("simon")
    private MessageService service;

    public void setService(MessageService service) {
        this.service = service;
    }

    public String processMessage(String message) {
        return this.service.sentMessage(message);
    }
}
