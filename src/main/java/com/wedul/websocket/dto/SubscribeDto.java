package com.wedul.websocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscribeDto {

    private SubscribeType type;
    private String name;
    private int count;
    private String message;

    @Builder
    public SubscribeDto(SubscribeType type, String name, int count, String message) {
        this.type = type;
        this.name = name;
        this.count = count;
        this.message = message;
    }
}
