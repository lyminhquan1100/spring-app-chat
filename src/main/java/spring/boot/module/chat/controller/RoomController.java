package spring.boot.module.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.boot.core.api.CoreController;
import spring.boot.core.api.ResponseDTO;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.service.RoomService;

@RequestMapping("/room")
@RestController
@CrossOrigin
public class RoomController extends CoreController<RoomDTO, RoomEntity> {
    @Autowired
    private RoomService roomService;

    @PostMapping("/join/{roomId}/{userId}")
    public ResponseDTO join(@PathVariable(name = "userId") Long userId,
                            @PathVariable(name = "roomId") Long roomId) {
        return response(roomService.join(userId, roomId));
    }

    @PostMapping("/join/{roomId}")
    public ResponseDTO joinMultiple(@PathVariable(name = "roomId") Long roomId,@RequestBody RoomDTO roomDTO) {
        return response(roomService.join(roomId, roomDTO));
    }
    @PostMapping("/leave/{roomId}/{userId}")
    public ResponseDTO leave(@PathVariable(name = "userId") Long userId,
                            @PathVariable(name = "roomId") Long roomId) {
        return response(roomService.leave(userId, roomId));
    }
    @PostMapping("/create")
    public ResponseDTO createRoom(@RequestBody RoomDTO roomDTO) {
        return response(roomService.create(roomDTO));
    }
}

