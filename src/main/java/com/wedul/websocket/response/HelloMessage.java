package com.wedul.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HelloMessage {

    private String name;

    @Builder
    public HelloMessage(String name) {
        this.name = name;
    }
}
