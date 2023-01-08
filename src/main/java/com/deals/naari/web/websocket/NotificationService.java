package com.deals.naari.web.websocket;

import com.deals.naari.web.websocket.dto.ResponseMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendGlobalNotification() {
        ResponseMessageDTO message = new ResponseMessageDTO("Global Notification");

        messagingTemplate.convertAndSend("/broad/tracker", message);
    }

    public void sendPrivateNotification(final String userId) {
        ResponseMessageDTO message = new ResponseMessageDTO("Private Notification");
        log.debug("sendPrivateNotification START - {}", userId);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(userId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSendToUser(userId, "Test Private Notification", "/userqueue/all", headerAccessor.getMessageHeaders());
    }
}
