package com.web.webSocket;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HelloMessage {
    private String name;

    // 기본 생성자
    public HelloMessage() {
    }

    // 추가 생성자
    public HelloMessage(String name) {
        this.name = name;
    }

}
