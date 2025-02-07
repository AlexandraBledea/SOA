package com.ubbcluj.task.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(final String topicSuffix, String message){
        messagingTemplate.convertAndSend("/topic/" + topicSuffix, message);
    }
}
