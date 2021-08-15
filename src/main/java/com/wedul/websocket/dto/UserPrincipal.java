package com.wedul.websocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Getter
@NoArgsConstructor
public class UserPrincipal implements Principal {
    private String name;

    @Builder
    public UserPrincipal(String name) {
        this.name = name;
    }
}
