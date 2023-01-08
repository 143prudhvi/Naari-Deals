package com.deals.naari.config;

import com.deals.naari.security.AuthoritiesConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebsocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            .nullDestMatcher()
            .permitAll()
            .simpSubscribeDestMatchers("/user/queue/errors", "/broadcast/**", "/usercast/**", "/user/**")
            .permitAll()
            .simpDestMatchers("/broadcast/**", "/usercast/**", "/user/**")
            .permitAll()
            .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE)
            .permitAll()
            .anyMessage()
            .denyAll();
        // .hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER)
        // matches any destination that starts with /broad/
        // (i.e. cannot send messages directly to /broad/)
        // (i.e. cannot subscribe to /broad/messages/* to get messages sent to
        // /broad/messages-user<id>)
        // .simpDestMatchers("/broadcast/**", "/usercast/**", "/user/**").permitAll()
        // message types other than MESSAGE and SUBSCRIBE
        // .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).permitAll()
        // catch all
        // .anyMessage().denyAll();
    }

    /**
     * Disables CSRF for Websockets.
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
