package ua.vadim;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.vadim.services.Bob;

public class ApplicationTest {
    String message = "test message";

    @Test
    public void simonSays() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Application app = context.getBean(Application.class);

        Assert.assertEquals("Simon says: " + message, app.processMessage(message));
    }

    @Test
    public void bobSays() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Bob app = context.getBean(Bob.class);

        Assert.assertEquals("Bob says: " + message, app.sentMessage(message));
    }
}
