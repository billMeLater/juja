package ua.vadim;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.vadim.services.Bob;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Application app = context.getBean(Application.class);

        System.out.println(app.processMessage("Hi there"));

        Bob bob = context.getBean(Bob.class);
        System.out.println(bob.sentMessage("it's a Bob"));
    }
}
