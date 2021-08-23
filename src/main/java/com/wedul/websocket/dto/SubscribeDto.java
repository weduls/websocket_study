package com.wedul.websocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscribeDto {

    private SubscribeType type;
    private String name;
    private int time;
    private String message;

    @Builder
    public SubscribeDto(SubscribeType type, String name, int time, String message) {
        this.type = type;
        this.name = name;
        this.time = time;
        this.message = message;
    }
}
