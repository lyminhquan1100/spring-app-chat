package spring.boot.module.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.controller.BaseController;
import spring.boot.module.chat.dto.LastSeenDTO;
import spring.boot.module.chat.service.LastSeenService;

@RequestMapping("/last-seen")
@RestController
@CrossOrigin
public class LastSeenController extends BaseController<LastSeenDTO, LastSeenService> {
    @Autowired
    private LastSeenService lastSeenService;

    @Override
    public LastSeenService getService() {
        return lastSeenService;
    }

    @MessageMapping("/last.seen.{userId}.{roomId}.{messageId}")
    public void lastSeen(@DestinationVariable Long userId,
                         @DestinationVariable Long roomId,
                         @DestinationVariable Long messageId) {
        lastSeenService.lastSeen(userId, roomId, messageId);
    }

}
