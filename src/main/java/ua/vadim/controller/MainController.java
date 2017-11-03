package ua.vadim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.vadim.services.MessageService;

@Controller
public class MainController {

    @Autowired
    @Qualifier("simon")
    private MessageService service;

    @RequestMapping(method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        String msg = "hi Bob";
        model.addAttribute("msg", service.sentMessage(msg));
        return "main";
    }
}
