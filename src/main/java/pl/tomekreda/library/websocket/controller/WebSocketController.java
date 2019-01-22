package pl.tomekreda.library.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.tomekreda.library.websocket.model.Hello;
import pl.tomekreda.library.websocket.model.User;


@Controller
public class WebSocketController {

    @MessageMapping("/notification")
    @SendTo("/app/notification")
    public Hello greeting(User user) throws Exception {
        return new Hello("Hi, " + user.getName() + "!");
    }
}