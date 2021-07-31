package com.web.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")   // hello 주소를 받으면 greeting 메소드를 실행할거야
    @SendTo("/topic/greetings")   // Greeting 이 반환되면 이제 /topic/greetings 를 구독하고 있는 사람들에게 모두 반환된다.
    public Greeting greeting(HelloMessage message) throws Exception {   //greeting 메소드를 실행하면서 HelloMessage 객체로 바운드
        Thread.sleep(1000);
        return new Greeting("Hello" + HtmlUtils.htmlEscape(message.getName()));
    }
}
