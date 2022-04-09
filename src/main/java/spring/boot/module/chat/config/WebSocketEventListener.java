package spring.boot.module.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import spring.boot.core.security.filter.JwtProvider;
import spring.boot.core.security.userdetail.UserPrincipal;
import spring.boot.module.auth.service.DeviceInfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        SimpMessageHeaderAccessor headerSimp = SimpMessageHeaderAccessor.wrap(event.getMessage());
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(
                headerSimp.getMessageHeaders().get("simpConnectMessage", Message.class));

        String token = headers.getNativeHeader("token").get(0);
        Authentication authentication = jwtProvider.getAuthentication(token);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (headers.getSessionAttributes() != null) {
            headers.getSessionAttributes().put("userId", userPrincipal.getId());
            headers.getSessionAttributes().put("deviceInfoId", userPrincipal.getDeviceInfoId());
        }
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");

        if (userId != null) {
            logger.info("User Disconnected : " + userId);

//            MessageDTO chatMessage = new MessageDTO();
//            chatMessage.setType(ChatMessage.MessageType.LEAVE);
//            chatMessage.setSenderId(userId);
//
//            messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
        }
    }

}