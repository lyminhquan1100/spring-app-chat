package spring.boot.module.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.module.auth.dto.LoginDTO;
import spring.boot.module.auth.repository.DeviceInfoRepository;
import spring.boot.module.auth.dto.DeviceInfoDTO;
import spring.boot.module.auth.entity.DeviceInfoEntity;
import spring.boot.module.chat.enums.PackEnum;
import spring.boot.module.chat.service.PackService;
import spring.boot.module.chat.service.RoomService;

@Service
public class DeviceInfoServiceImpl
        extends CoreServiceImpl<DeviceInfoDTO, DeviceInfoEntity>
        implements DeviceInfoService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private DeviceInfoRepository repository;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PackService packService;

    @Override
    public void logoutDevice(Long id, LoginDTO loginDTO, SimpMessageHeaderAccessor headerAccessor) {
        DeviceInfoDTO deviceInfo = deviceInfoService.findById(id);
        if (!accountService.verifyUser(loginDTO.getUsername(), loginDTO.getPassword())) {
            packService.sendBackCurrentDevice(headerAccessor, PackEnum.WARNING, "Mật khẩu không đúng");
            return;
        }
        if (deviceInfo != null) {
            deviceInfoService.delete(id);
            packService.sendBackCurrentDevice(headerAccessor, PackEnum.SUCCESS_LOGOUT, deviceInfo);
            packService.sendToDevice(deviceInfo.getId(), PackEnum.LOGOUT_DEVICE, null);
        }
    }

    @Override
    public void updateCoords(Long id, DeviceInfoDTO deviceInfoDTO) {
        DeviceInfoEntity deviceInfoEntity = repository.findById(id).orElse(null);
        if (deviceInfoEntity != null) {
            deviceInfoEntity.setLatitude(deviceInfoEntity.getLatitude());
            deviceInfoEntity.setLongitude(deviceInfoEntity.getLongitude());
            repository.save(deviceInfoEntity);
            packService.sendPublic(PackEnum.COORDS_UPDATE, mapToDTO(deviceInfoEntity));
        }
    }

    @Override
    public Boolean checkLogoutDevice(Long id) {
        return !getRepository().existsById(id);
    }
}