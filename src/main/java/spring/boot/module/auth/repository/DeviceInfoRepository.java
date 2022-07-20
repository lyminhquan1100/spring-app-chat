package spring.boot.module.auth.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.core.api.CoreRepository;
import spring.boot.module.auth.dto.DeviceInfoDTO;
import spring.boot.module.auth.entity.DeviceInfoEntity;

@Repository
public interface DeviceInfoRepository extends CoreRepository<DeviceInfoDTO, DeviceInfoEntity> {

    @Query("select e from DeviceInfoEntity e" +
            " where (e.accountId = :#{#dto.accountId}) " +
            " and (e.ip = :#{#dto.ip} or :#{#dto.ip} is null)" +
            " and (e.nameDevice = :#{#dto.nameDevice} or :#{#dto.nameDevice} is null) " +
            " and (e.address = :#{#dto.address} or :#{#dto.address} is null) " +
            " and (e.application = :#{#dto.application}) "
    )
    DeviceInfoEntity findByDTO(DeviceInfoDTO dto);

    @Override
    @Query("select e from DeviceInfoEntity e" +
            " where (e.accountId = :#{#dto.accountId} or :#{#dto.accountId} is null) " +
            " and (e.ip = :#{#dto.ip} or :#{#dto.ip} is null)" +
            " and (e.nameDevice = :#{#dto.nameDevice} or :#{#dto.nameDevice} is null) " +
            " and (e.address = :#{#dto.address} or :#{#dto.address} is null) " +
            " and (e.application = :#{#dto.application} or :#{#dto.application} is null) "
    )
    Page<DeviceInfoEntity> search(DeviceInfoDTO dto, Pageable pageable);
}