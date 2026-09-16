package com.qbatz.payment.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/send")
    @SendTo("/consume/message")
    public String sendStatus() {
        return "success";
    }
}
