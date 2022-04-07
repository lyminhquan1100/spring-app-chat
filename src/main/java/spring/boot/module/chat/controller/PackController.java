package spring.boot.module.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.module.auth.dto.DeviceInfoDTO;
import spring.boot.module.auth.dto.LoginDTO;
import spring.boot.module.auth.service.DeviceInfoService;
import spring.boot.module.chat.dto.EmojiDTO;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.service.*;

@RestController
public class PackController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private LastSeenService lastSeenService;

    @Autowired
    private EmojiService emojiService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @MessageMapping("/list.room.{userId}")
    public void listRoom(@DestinationVariable Long userId) {
        roomService.listRoom(userId);
    }

    @MessageMapping("/send.message")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        messageService.sendMessage(messageDTO);
    }

    @MessageMapping("/last.seen.{userId}.{roomId}.{messageId}")
    public void lastSeen(@DestinationVariable Long userId,
                         @DestinationVariable Long roomId,
                         @DestinationVariable Long messageId) {
        lastSeenService.lastSeen(userId, roomId, messageId);
    }

    @MessageMapping("/send.emoji")
    public void sendEmoji(@Payload EmojiDTO emojiDTO) {
        emojiService.sendEmoji(emojiDTO);
    }


    @MessageMapping("/logout.device.{id}")
    public void logoutDevice(@DestinationVariable Long id,
                             @Payload LoginDTO loginDTO,
                             SimpMessageHeaderAccessor headerAccessor) {
        deviceInfoService.logoutDevice(id,loginDTO,headerAccessor);
    }

    @MessageMapping("/update.coords.{id}")
    public void updateCoords(@DestinationVariable Long id,
                             @Payload DeviceInfoDTO deviceInfoDTO,
                             SimpMessageHeaderAccessor headerAccessor) {
        deviceInfoService.updateCoords(id,deviceInfoDTO);
    }

    @SubscribeMapping("/app/device/{id}")
    public void subscribeDevice(@DestinationVariable Long id) {
        deviceInfoService.checkLogoutDevice(id);
    }
}
