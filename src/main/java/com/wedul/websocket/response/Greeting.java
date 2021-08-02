package com.wedul.websocket.response;

import lombok.Getter;

@Getter
public class Greeting {

    private final String content;

    public Greeting(String content) {
        this.content = content;
    }
}
