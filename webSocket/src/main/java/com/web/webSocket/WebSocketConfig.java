package com.web.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker    // MessageBroker을 이용해서 WebSocket을 이용가능하게 해준다
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메모리 기반 메시지 브로커가 접두사가 붙은 대상의 클라이언트에 인사말 메시지를 다시 전달할 수 있도록 호출해줌
        config.enableSimpleBroker("/topic");
        // @MessageMapping 어노테이션에 /app 접두사를 붙여준다.
        // 예) /app/hello 는 GreetingController.greeting() 메소드 매핑의 endpoint 가 됨
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String[] addressList = {"http://localhost:8081"}; // 허용할 주소 리스트

        //WebSocket을 사용할 수 없을 때 대체 전송을 사용할 수 있도록 해줌
        // SockJS 폴백 옵션을 활성화 해서 endpoint 등록
        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins(addressList).withSockJS();
    }
}
