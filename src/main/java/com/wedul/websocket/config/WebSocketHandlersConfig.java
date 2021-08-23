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

package com.wedul.websocket.config;

import com.wedul.websocket.handler.WebSocketSubscribeHandler;
import com.wedul.websocket.handler.WebSocketUnSubscribeHandler;
import com.wedul.websocket.repository.UserDeliveryTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.websocket.Session;

/**
 * These handlers are separated from WebSocketConfig because they are specific to this
 * application and do not demonstrate a typical Spring Session setup.
 *
 * @author Rob Winch
 */
@Configuration
public class WebSocketHandlersConfig<S extends Session> {

	@Bean
	public WebSocketSubscribeHandler<S> webSocketSubscribeHandler(UserDeliveryTimeService userDeliveryTimeService, SimpMessagingTemplate messageTemplate) {
		return new WebSocketSubscribeHandler<>(messageTemplate, userDeliveryTimeService);
	}

	@Bean
	public WebSocketUnSubscribeHandler<S> webSocketUnSubscribeHandler(UserDeliveryTimeService userDeliveryTimeService) {
		return new WebSocketUnSubscribeHandler<>(userDeliveryTimeService);
	}

}
