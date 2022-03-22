package spring.boot.module.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        MessageHeaders messageHeaders = headers.getMessageHeaders();
        GenericMessage simpConnect = messageHeaders.get("simpConnectMessage", GenericMessage.class);
        Map<String, String> headerReq = simpConnect.getHeaders().get("nativeHeaders", Map.class);

        Object userId = headerReq.get("userId");

//        if (userId != null) {
////            headers.getMessageHeaders().im
////            headers.getUser();
////            headers.getMessageHeaders().put("userId" , userId);
//            if(headers.getSessionAttributes() != null){
//                headers.getSessionAttributes().put("userId", userId);
//            }else{
//                Map<String,Object> mapSession = new HashMap<String,Object>() {{
//                    put("userId",userId);
//                }};
////                Map mapSession = Collections.singletonMap("userId", userId);
//                headers.setImmutable();
//                headers.setSessionAttributes(mapSession);
//            }
//        }

//        String chatRoomId = headers.getNativeHeader("userId").get(0);
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