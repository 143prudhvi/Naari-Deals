package com.deals.naari.web.websocket;

import static com.deals.naari.config.WebsocketConfiguration.IP_ADDRESS;

import com.deals.naari.web.websocket.dto.ActivityDTO;
import com.deals.naari.web.websocket.dto.MessageDTO;
import com.deals.naari.web.websocket.dto.ResponseMessageDTO;
import java.security.Principal;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ActivityService implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private NotificationService notificationService;

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public ActivityService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/broadcast")
    @SendTo("/broadcast/all")
    public ActivityDTO sendActivity(@Payload ActivityDTO activityDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        activityDTO.setUserLogin(principal.getName());
        activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
        activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
        activityDTO.setTime(Instant.now());
        log.debug("Sending user tracking data {}", activityDTO);
        return activityDTO;
    }

    @MessageMapping("/usercast")
    @SendToUser("/userqueue/all")
    public ResponseMessageDTO getPrivateMessage(final MessageDTO message, final Principal principal) throws InterruptedException {
        log.debug("getPrivateMessage START");
        return new ResponseMessageDTO(
            HtmlUtils.htmlEscape("Sending private message to user " + principal.getName() + ": " + message.getMessageContent())
        );
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setSessionId(event.getSessionId());
        activityDTO.setPage("logout");
        log.debug("onApplicationEvent START");
        notificationService.sendPrivateNotification("teja");
        // messagingTemplate.convertAndSend("/broad/all", activityDTO);
        // messagingTemplate.convertAndSendToUser(event1.getAuthentication().getName(), "activityDTO", "/userqueue/all");
    }
}
