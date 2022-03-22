package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.boot.core.exception.BaseException;
import spring.boot.core.service.AbstractBaseService;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.entity.LastSeenEntity;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.repository.LastSeenRepository;
import spring.boot.module.chat.repository.MessageRepository;
import spring.boot.module.chat.repository.RoomRepository;
import spring.boot.module.chat.utils.Destinations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl extends AbstractBaseService<RoomEntity, RoomDTO, RoomRepository> implements RoomService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LastSeenRepository lastSeenRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Override
    protected RoomRepository getRepository() {
        return roomRepository;
    }

    @Override
    protected void beforeSave(RoomEntity entity, RoomDTO dto) {
        super.beforeSave(entity, dto);

    }

    private RoomDTO joinOrLeave(Long userId, Long roomId, boolean isJoin) {
        if (userId == null) {
            throw new BaseException("Vui lòng nhập userId");
        }
        if (roomId == null) {
            throw new BaseException("Vui lòng nhập roomId");
        }
        RoomEntity roomEntity = getRepository().findById(roomId).orElse(null);
        if (userId == roomEntity.getAdminId()) {
            sendMessage(accountService.getCurrentUserId(), Destinations.sendData("warning", "Quản trị viên đã trong nhóm"));
            return null;
        }
        for (int i = 0; i < roomEntity.getConnectedUsers().size(); i++) {
            if (roomEntity.getConnectedUsers().get(i).getId() == userId) {
                sendMessage(accountService.getCurrentUserId(), Destinations.sendData("warning", "Người dùng đang trong nhóm"));
                return null;
            }
        }
        AccountEntity accountEntity = accountRepository.findById(userId).orElse(null);
        if (roomEntity != null) {
            if (isJoin) {
                roomEntity.addUser(accountEntity);
            } else {
                roomEntity.removeUser(accountEntity);
            }
            RoomDTO roomDTO = new RoomDTO();
            save(roomEntity, roomDTO);
            if (roomEntity.getConnectedUsers() == null) {
                List<AccountEntity> newList = new ArrayList<>();
                roomEntity.setConnectedUsers(newList);
            }
//            List<Long> userIds = roomEntity.getConnectedUsers()
//                    .stream().map(e -> e.getId()).collect(Collectors.toList());
//            userIds.add(roomEntity.getAdminId());
//            SendDTO sendDTO = new SendDTO("addUser", accountEntity);
            sendMessage(userId, Destinations.sendData("join", roomEntity));
//            sendMessage(userIds, Destinations.sendData("addUser", roomEntity));
        }
        return mapToDTO(roomEntity);
    }

    @Override
    public RoomDTO join(Long userId, Long roomId) {
        return joinOrLeave(userId, roomId, true);
    }

    @Override
    public RoomDTO leave(Long userId, Long roomId) {
        return joinOrLeave(userId, roomId, false);
    }

    @Override
    public void sendMessage(MessageDTO messageDTO) {
        if (messageDTO.getRoomId() == null) {
            throw new BaseException("Chưa nhập roomId");
        }
        RoomEntity roomEntity = getRepository()
                .findById(messageDTO.getRoomId()).orElse(null);
        if (roomEntity != null) {
            messageService.save(messageDTO);
            RoomDTO roomDTO = findById(messageDTO.getRoomId());
            if (roomDTO == null) {
                throw new BaseException("Room không tồn tại");
            }
            roomDTO.setLastMessageId(messageDTO.getId());
            save(roomDTO);

            simpMessagingTemplate.convertAndSend(
                    Destinations.userMessage(roomEntity.getAdminId()),
                    Destinations.sendData("chat", messageDTO));
            List<AccountEntity> listUser = roomEntity.getConnectedUsers();
            if (listUser != null) {
                for (int i = 0; i < listUser.size(); i++) {
                    simpMessagingTemplate.convertAndSend(
                            Destinations.userMessage(listUser.get(i).getId()),
                            Destinations.sendData("chat", messageDTO));
                }
            }
        }
    }

    @Override
    public void sendMessage(Long roomId, String type, Object payload) {
        assert roomId != null;
        RoomEntity roomEntity = getRepository().findById(roomId).orElse(null);
        if (roomEntity != null) {
            RoomDTO roomDTO = findById(roomId);
            if (roomDTO == null) {
                throw new BaseException("Room không tồn tại");
            }

            simpMessagingTemplate.convertAndSend(
                    Destinations.userMessage(roomEntity.getAdminId()),
                    Destinations.sendData(type, payload));
            List<AccountEntity> listUser = roomEntity.getConnectedUsers();
            if (listUser != null) {
                for (int i = 0; i < listUser.size(); i++) {
                    simpMessagingTemplate.convertAndSend(
                            Destinations.userMessage(listUser.get(i).getId()),
                            Destinations.sendData(type, payload));
                }
            }
        }
    }

    private void sendMessage(Long userId, Map<String, Object> sendDto) {
        simpMessagingTemplate.convertAndSend(
                Destinations.userMessage(userId), sendDto);

    }

    private void sendMessage(List<Long> userIds, Map<String, Object> sendDto) {
        for (int i = 0; i < userIds.size(); i++) {
            simpMessagingTemplate.convertAndSend(
                    Destinations.userMessage(userIds.get(i)), sendDto);
        }
    }

    @Override
    public void subscribe(Map<String, Object> body) {
        String userId = body.get("userId").toString();
        simpMessagingTemplate.convertAndSend(Destinations
                .userMessage(userId), "subcribe success");
//        subscribe(body);
    }

    @Override
    public void subscribe(Long userId) {

        simpMessagingTemplate.convertAndSend(Destinations
                .userMessage(userId), "subcribe success");
//        subscribe(body);
    }

    @Override
    public void listRoom(Long userId) {
        List<RoomDTO> roomDTOList = getRepository()
                .listRoomByUserId(userId)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
        simpMessagingTemplate.convertAndSend(Destinations
                .userMessage(userId), Destinations.sendData("listRoom", roomDTOList));
//        subscribe(body);
    }

    @Override
    protected void specificMapToDTO(RoomEntity entity, RoomDTO dto) {
        super.specificMapToDTO(entity, dto);

        if (dto.getLastMessageId() != null) {
            LastSeenEntity lastSeenEntity = lastSeenRepository
                    .findById(dto.getLastMessageId()).orElse(null);
            if (lastSeenEntity != null) {
                dto.setLastMessage(lastSeenEntity);
            }
        }
        if (entity.getAdminId() != null) {
            AccountEntity accountEntity = accountRepository.findById(
                    entity.getAdminId()
            ).orElse(null);
            dto.setAdmin(accountEntity);
        }
    }
}
