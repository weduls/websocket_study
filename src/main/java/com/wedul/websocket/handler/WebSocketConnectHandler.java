/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wedul.websocket.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.security.Principal;

public class WebSocketConnectHandler<S> implements ApplicationListener<SessionConnectEvent> {

	private RedisMessageListenerContainer container;
	private RedisSubscriber subscriber;

	public WebSocketConnectHandler() {
		super();
	}

	public WebSocketConnectHandler(RedisMessageListenerContainer container, RedisSubscriber subscriber) {
		this.container = container;
		this.subscriber = subscriber;
	}

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		MessageHeaders headers = event.getMessage().getHeaders();
		Principal user = SimpMessageHeaderAccessor.getUser(headers);
		if (user == null) {
			return;
		}
	}

}
