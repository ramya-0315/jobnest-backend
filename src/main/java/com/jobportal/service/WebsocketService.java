package com.jobportal.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebsocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendJobAlert(String message) {
        messagingTemplate.convertAndSend("/topic/job-alerts", message);
    }
}
