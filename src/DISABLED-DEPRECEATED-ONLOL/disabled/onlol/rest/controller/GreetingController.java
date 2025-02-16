package status.disabled.onlol.rest.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Object greeting(Object message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello,  xdddd!";
    }
}
