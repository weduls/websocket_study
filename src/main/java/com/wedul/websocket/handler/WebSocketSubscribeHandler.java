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

import com.wedul.websocket.dto.SubscribeDto;
import com.wedul.websocket.dto.SubscribeType;
import com.wedul.websocket.repository.UserDeliveryTimeService;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public class WebSocketSubscribeHandler<S> implements ApplicationListener<SessionSubscribeEvent> {

	private SimpMessagingTemplate messageTemplate;
	private UserDeliveryTimeService userDeliveryTimeService;
	private static final String TARGET = "/topic/data";

	public WebSocketSubscribeHandler() {
		super();
	}

	public WebSocketSubscribeHandler(SimpMessagingTemplate messageTemplate, UserDeliveryTimeService userDeliveryTimeService) {
		this.userDeliveryTimeService = userDeliveryTimeService;
		this.messageTemplate = messageTemplate;
	}

	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		String source = String.valueOf(((List) ((Map) event.getMessage().getHeaders().get("nativeHeaders")).get("destination")).get(0));

		if (source.contains(TARGET)) {
			Principal user = event.getUser();
			if (user == null || user.getName() == null) {
				return;
			}
			messageTemplate.convertAndSendToUser(user.getName(), TARGET, SubscribeDto.builder()
					.type(SubscribeType.USER)
					.time(userDeliveryTimeService.getTime(user.getName(), 20))
					.build()
			);
		}
	}
}
