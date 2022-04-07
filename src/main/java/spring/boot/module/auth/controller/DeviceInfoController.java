package spring.boot.module.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.api.CoreController;
import spring.boot.module.auth.dto.DeviceInfoDTO;
import spring.boot.module.auth.entity.DeviceInfoEntity;

@RequestMapping("/device-info")
@RestController
@CrossOrigin
public class DeviceInfoController extends CoreController<DeviceInfoDTO, DeviceInfoEntity> {

}
