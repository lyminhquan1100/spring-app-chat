package spring.boot.module.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.chat.service.RoomService;

@Component
public class WebSocketEvents {

    @Autowired
    private RoomService roomService;

    @Autowired
    private AccountService accountService;

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Long chatRoomId = Long.valueOf(headers.getNativeHeader("chatRoomId").get(0));
        Long userId = Long.valueOf(headers.getNativeHeader("userId").get(0));
        headers.getSessionAttributes().put("chatRoomId", chatRoomId);
        AccountDTO joiningUser = accountService.findById(userId);

        roomService.join(joiningUser, roomService.findById(chatRoomId));
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Long chatRoomId = Long.valueOf(headers.getNativeHeader("chatRoomId").get(0));
        Long userId = Long.valueOf(headers.getNativeHeader("userId").get(0));
        AccountDTO leavingUser = accountService.findById(userId);

        roomService.leave(leavingUser, roomService.findById(chatRoomId));
    }
}
