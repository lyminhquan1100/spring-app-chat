package spring.boot.module.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.core.exception.BaseException;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.entity.MessageEntity;
import spring.boot.module.chat.entity.RoomEntity;
import spring.boot.module.chat.enums.PackEnum;
import spring.boot.module.chat.repository.LastSeenRepository;
import spring.boot.module.chat.repository.MessageRepository;
import spring.boot.module.chat.repository.RoomRepository;
import spring.boot.module.chat.utils.Destinations;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl extends CoreServiceImpl<RoomDTO, RoomEntity>
        implements RoomService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private PackService packService;

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
        AccountEntity accountEntity = accountRepository.findById(userId).orElse(null);
        duplicateUser(roomEntity, accountEntity);

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
        return mapToDTO(roomEntity);
    }

    @Override
    public RoomDTO join(Long userId, Long roomId) {
        return joinOrLeave(userId, roomId, true);
    }

    @Override
    public RoomDTO join(Long roomId, RoomDTO roomDTO) {
        List<Long> listUserId = roomDTO.getIdAddUsers();
        if (listUserId == null || listUserId.isEmpty()) {
            throw new BaseException("Chưa chọn người dùng");
        }
        RoomEntity roomEntity = getRepository().findById(roomId).orElse(null);
        List<AccountEntity> accountEntityList = new ArrayList<>();
        for (Long idUser : listUserId) {
            AccountEntity accountEntity = accountRepository.findById(idUser).orElse(null);
            duplicateUser(roomEntity, accountEntity);
            accountEntityList.add(accountEntity);
        }
        for (AccountEntity accountEntity : accountEntityList) {
            roomEntity.addUser(accountEntity);
        }

        RoomDTO room = mapToDTO(save(roomEntity, roomDTO));
        packService.sendToUser(listUserId, PackEnum.JOIN, room);
        return room;
    }

    @Override
    public RoomDTO leave(Long userId, Long roomId) {
        return joinOrLeave(userId, roomId, false);
    }

    private void sendMessage(Long userId, Map<String, Object> sendDto) {
        simpMessagingTemplate.convertAndSend(
                Destinations.userPack(userId), sendDto);

    }

    private void sendMessage(List<Long> userIds, Map<String, Object> sendDto) {
        for (int i = 0; i < userIds.size(); i++) {
            simpMessagingTemplate.convertAndSend(
                    Destinations.userPack(userIds.get(i)), sendDto);
        }
    }

    @Override
    public void listRoom(Long userId) {
        List<RoomDTO> roomDTOList = roomRepository
                .listRoomByUserId(userId)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
        packService.sendToUser(userId, PackEnum.LIST_ROOM, roomDTOList);
    }

    @Override
    public RoomDTO create(RoomDTO roomDTO) {
        if (roomDTO.getIdAddUsers() == null) {
            throw new BaseException("Chưa chọn danh sách người dùng");
        }

        Set<Long> idFilter = new HashSet<>();
        idFilter.add(roomDTO.getAdminId());
        idFilter.addAll(roomDTO.getIdAddUsers());
        List<Long> listId = new ArrayList<>(idFilter);
        List<RoomEntity> listRoom = null;
        if (listId.size() == 1) {// chỉ có admin
            listRoom = roomRepository.findByAdminId(roomDTO.getAdminId());
            for (RoomEntity room : listRoom) {
                if (room.getConnectedUsers().size() == 0) {
                    return mapToDTO(room);
                }
            }
        } else {
            listRoom = roomRepository.findBylistUserConnect(listId);
            for (RoomEntity room : listRoom) {
                return mapToDTO(room);
            }
        }


        idFilter.remove(roomDTO.getAdminId());
        List<Long> listConnect = new ArrayList<>(idFilter);

        roomDTO.setConnectedUsers(listConnect
                .stream().map(e -> accountService.findById(e))
                .collect(Collectors.toList()));
        RoomDTO room = save(roomDTO);
        packService.sendToUser(listConnect, PackEnum.JOIN, room);
        return room;
    }

    @Override
    public RoomDTO mapToDTO(RoomEntity entity) {
        RoomDTO dto = super.mapToDTO(entity);

        if (dto.getLastMessageId() != null) {
            MessageEntity messageEntity = messageRepository
                    .findById(dto.getLastMessageId()).orElse(null);
            if (messageEntity != null) {
                dto.setLastMessage(messageService.mapToDTO(messageEntity));
            }
        }
        if (entity.getAdminId() != null) {
            AccountEntity accountEntity = accountRepository.findById(
                    entity.getAdminId()
            ).orElse(null);
            dto.setAdmin(accountEntity);
        }
        return dto;
    }

    private void duplicateUser(RoomEntity roomEntity, AccountEntity accountEntity) {
        if (roomEntity == null) {
            throw new BaseException("Vui lòng nhập roomId");
        }
        if (accountEntity == null) {
            throw new BaseException("Thông tin người dùng không chính xác");
        }
        if (accountEntity.getId().equals(roomEntity.getAdminId())) {
//            sendMessage(accountService.getCurrentUserId(), Destinations.sendData("warning", "Quản trị viên đã trong nhóm"));
            throw new BaseException("Quản trị viên đã trong nhóm");
        }
        if (roomEntity.getConnectedUsers() != null) {
            for (int i = 0; i < roomEntity.getConnectedUsers().size(); i++) {
                if (roomEntity.getConnectedUsers().get(i).getId().equals(accountEntity.getId())) {
//                    sendMessage(accountService.getCurrentUserId(), Destinations.sendData("warning", "Người dùng đang trong nhóm"));
                    throw new BaseException("Người dùng đang trong nhóm");
                }
            }
        }
    }
}
