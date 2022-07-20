package spring.boot.module.chat.controller;

import org.springframework.web.bind.annotation.*;
import spring.boot.core.api.CoreController;
import spring.boot.core.api.ResponseDTO;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.service.RoomService;

@RequestMapping("/room")
@RestController
@CrossOrigin
public class RoomController extends CoreController<RoomDTO, RoomEntity,RoomService> {

    public RoomController(RoomService s) {
        super(s);
    }

    @PostMapping("/join/{roomId}/{userId}")
    public ResponseDTO join(@PathVariable(name = "userId") Long userId,
                            @PathVariable(name = "roomId") Long roomId) {
        return response(service.join(userId, roomId));
    }

    @PostMapping("/join/{roomId}")
    public ResponseDTO joinMultiple(@PathVariable(name = "roomId") Long roomId,@RequestBody RoomDTO roomDTO) {
        return response(service.join(roomId, roomDTO));
    }
    @PostMapping("/leave/{roomId}/{userId}")
    public ResponseDTO leave(@PathVariable(name = "userId") Long userId,
                            @PathVariable(name = "roomId") Long roomId) {
        return response(service.leave(userId, roomId));
    }
    @PostMapping("/create")
    public ResponseDTO createRoom(@RequestBody RoomDTO roomDTO) {
        return response(service.create(roomDTO));
    }
}

