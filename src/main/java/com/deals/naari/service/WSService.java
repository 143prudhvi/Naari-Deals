package com.deals.naari.service;

import com.deals.naari.service.dto.PrivateOutgoingMessage;
import com.deals.naari.service.dto.ResponseMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    // private final NotificationService notificationService;
    private final Logger log = LoggerFactory.getLogger(WSService.class);

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        // this.notificationService = notificationService;
    }

    public void notifyFrontend(final String message) {
        ResponseMessageDTO response = new ResponseMessageDTO(message);
        // notificationService.sendGlobalNotification();
        PrivateOutgoingMessage msg = new PrivateOutgoingMessage();
        msg.setMessage("This is Test User Driven Broadcast message");
        messagingTemplate.convertAndSend("/broadcast/all", msg);
    }

    public void notifyUser(final String username, final PrivateOutgoingMessage message) {
        // ResponseMessageDTO response = new ResponseMessageDTO(message);
        log.debug("notifyUser: {} - {}", username, message.getMessage());
        // notificationService.sendPrivateNotification(id);
        messagingTemplate.convertAndSendToUser(username, "/userqueue/all", message);
    }
}
