package ua.vadim;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.vadim.services.Bob;

public class ApplicationTest {
    String message = "test message";
    ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

    @Test
    public void simonSays() {
        Application app = context.getBean(Application.class);
        Assert.assertEquals("Simon says: " + message, app.processMessage(message));
    }

    @Test
    public void bobSays() {
        Bob app = context.getBean(Bob.class);
        Assert.assertEquals("Bob says: " + message, app.sentMessage(message));
    }
}
