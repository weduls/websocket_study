package com.wedul.websocket.controller;

import com.wedul.websocket.response.Greeting;
import com.wedul.websocket.response.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    // simple broker를 사용하려면 Broker 설정에서 사용한 config에 맞는 값을 사용해야한다.
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) {
        return new Greeting("Hello! " + HtmlUtils.htmlEscape(message.getName()));
    }

}
