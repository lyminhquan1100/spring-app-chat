package spring.boot.module.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import spring.boot.core.api.CoreEntity;

@Entity
@Table(name = "device_info")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class DeviceInfoEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    @Column(length = 15)
    private String ip;

    private String nameDevice;

    private String address;

    private String application;

    private Double latitude;

    private Double longitude;
}