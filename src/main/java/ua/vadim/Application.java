package ua.vadim;

import ua.vadim.services.MessageService;

public class Application {
    private MessageService service;

    public void setService(MessageService service) {
        this.service = service;
    }

    public String processMessage(String message) {
        return this.service.sentMessage(message);
    }
}
