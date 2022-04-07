package spring.boot.module.auth.service;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import spring.boot.core.api.CoreService;
import spring.boot.module.auth.dto.DeviceInfoDTO;
import spring.boot.module.auth.dto.LoginDTO;
import spring.boot.module.auth.entity.DeviceInfoEntity;

public interface DeviceInfoService extends CoreService<DeviceInfoDTO, DeviceInfoEntity> {
    void logoutDevice(Long id, LoginDTO loginDTO, SimpMessageHeaderAccessor headerAccessor);
    void updateCoords(Long id, DeviceInfoDTO deviceInfoDTO);
    void checkLogoutDevice(Long id);
}