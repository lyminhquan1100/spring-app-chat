package spring.boot.module.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.core.api.CoreDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfoDTO extends CoreDTO {
    private Long id;

    private Long accountId;

    private String ip;

    private String nameDevice;

    private String address;

    private String application;
}
