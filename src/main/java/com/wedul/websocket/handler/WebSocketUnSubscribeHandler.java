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

import com.wedul.websocket.repository.UserDeliveryTimeService;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public class WebSocketUnSubscribeHandler<S> implements ApplicationListener<SessionUnsubscribeEvent> {

	private UserDeliveryTimeService userDeliveryTimeService;
	private static final String TARGET = "/topic/data";

	public WebSocketUnSubscribeHandler() {
		super();
	}

	public WebSocketUnSubscribeHandler(UserDeliveryTimeService userDeliveryTimeService) {
		this.userDeliveryTimeService = userDeliveryTimeService;
	}

	@Override
	public void onApplicationEvent(SessionUnsubscribeEvent event) {
		String source = String.valueOf(((List) ((Map) event.getMessage().getHeaders().get("nativeHeaders")).get("destination")).get(0));

		if (source.contains(TARGET)) {
			Principal user = event.getUser();
			if (user == null || user.getName() == null) {
				return;
			}
			userDeliveryTimeService.deleteTime(user.getName());
		}
	}
}
