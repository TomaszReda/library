package pl.tomekreda.library.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.tomekreda.library.websocket.model.Response;


@Controller
public class WebSocketController {

    @MessageMapping("/notification")
    @SendTo("/app/notification")
    public Response greeting() throws Exception {
        return new Response("New notification!");
    }
}