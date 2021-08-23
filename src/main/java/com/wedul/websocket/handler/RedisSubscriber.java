package com.wedul.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedul.websocket.dto.SubscribeDto;
import com.wedul.websocket.dto.SubscribeType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messageTemplate;

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String subscribeMessage = new String(message.getBody());
        SubscribeDto subscribe = objectMapper.readValue(subscribeMessage, SubscribeDto.class);
        log.info("[subscribe][message] {}", subscribeMessage);

        if (SubscribeType.BROAD_CAST == subscribe.getType()) {
            messageTemplate.convertAndSend("/topic/message", subscribe);
        }
        messageTemplate.convertAndSendToUser(subscribe.getName(), "/topic/data", subscribe);
    }
}
