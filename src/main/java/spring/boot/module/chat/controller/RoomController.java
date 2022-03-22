package spring.boot.module.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.boot.core.controller.BaseController;
import spring.boot.core.dto.ResponseDTO;
import spring.boot.module.auth.dto.AccountDTO;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.service.RoomService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequestMapping("/room")
@RestController
@CrossOrigin
public class RoomController extends BaseController<RoomDTO, RoomService> {
    @Autowired
    private RoomService roomService;

    @Override
    public RoomService getService() {
        return roomService;
    }

    @PostMapping("/join/{roomId}/{userId}")
    public ResponseDTO join(@PathVariable(name = "userId") Long userId,
                            @PathVariable(name = "roomId") Long roomId) {
        return response(getService().join(userId, roomId));
    }
    @PostMapping("/leave/{roomId}/{userId}")
    public ResponseDTO leave(@PathVariable(name = "userId") Long userId,
                            @PathVariable(name = "roomId") Long roomId) {
        return response(getService().leave(userId, roomId));
    }


    @MessageMapping("/send.message")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        getService().sendMessage(messageDTO);
    }

//    @SubscribeMapping("/user")
//    public void subscribe(@Payload Map<String,Object> dto) {
//        getService().subscribe(dto);
////        getService().sendMessage(messageDTO);
//    }

//    @SubscribeMapping("/user.{userId}")
//    public void subscribe3(@DestinationVariable Long userId, @Payload Map<String,Object> dto) {
//        getService().subscribe(userId);
////        getService().sendMessage(messageDTO);
//    }

    @MessageMapping("/list.room.{userId}")
    public void listRoom(@DestinationVariable Long userId) {
        getService().listRoom(userId);
//        getService().sendMessage(messageDTO);
    }

//    @PostMapping("/add-user")
//    public ResponseDTO addUser(@RequestBody RoomDTO dto){
//        return response(getService().addUser(dto));
//    }

//    @PostMapping("/join")
//    public ResponseDTO join(@RequestBody RoomDTO dto){
//        return response(getService().joinTeamRes(dto));
//    }

//    @PutMapping("/join")
//    public ResponseDTO join(@RequestBody RoomDTO roomDTO) {
//        return response(roomService.findById(roomDTO.getId()));
//    }
//
//
//    @SubscribeMapping("/connected.users")
//    public List<AccountDTO> listChatRoomConnectedUsersOnSubscribe(SimpMessageHeaderAccessor headerAccessor) {
//        Long chatRoomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
//        return roomService.findById(chatRoomId).getConnectedUsers();
//    }
//
//
//    @MessageMapping("/send.message")
//    public void sendMessage(@Payload MessageDTO messageDTO,
//                            SimpMessageHeaderAccessor headerAccessor) {
//        Long chatRoomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
//        messageDTO.setRoomId(chatRoomId);
//
//        if (messageDTO.isPublic()) {
//            roomService.sendPublicMessage(messageDTO);
//        } else {
//            roomService.sendPrivateMessage(messageDTO);
//        }
//    }
}

