package com.wedul.websocket.controller;

import com.wedul.websocket.dto.UserMessage;
import com.wedul.websocket.response.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final SimpMessagingTemplate messageTemplate;

    @MessageMapping("/message")
    // simple broker를 사용하려면 Broker 설정에서 사용한 config에 맞는 값을 사용해야한다.
    @SendToUser("/topic/message")
    public Greeting greetingMessage(UserMessage message) {
        return new Greeting(HtmlUtils.htmlEscape("To "+ message.getTargetUserName() + ", " + message.getMessage()));
    }

    @MessageMapping("/message/sendToUser")
    // simple broker를 사용하려면 Broker 설정에서 사용한 config에 맞는 값을 사용해야한다.
    public void greeting(UserMessage message) {
        messageTemplate.convertAndSendToUser(message.getTargetUserName(), "/topic/data", new Greeting(HtmlUtils.htmlEscape(message.getMessage())));
    }

}
