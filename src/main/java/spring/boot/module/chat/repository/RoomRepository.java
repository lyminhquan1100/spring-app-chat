package spring.boot.module.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.core.dao.repository.BaseRepository;
import spring.boot.module.chat.dto.RoomDTO;
import spring.boot.module.chat.entity.RoomEntity;

import java.util.List;

@Repository
public interface RoomRepository extends BaseRepository<RoomEntity, RoomDTO, Long> {
//    @Override
//    @Query("select e from RoomEntity e" +
//            " where 1=1" +
//            " and (exists(select u from e.connectedUsers u" +
//            "                   where u.id = :#{#dto.userId})" +
//            "       or :#{#dto.userId} = e.idLeader or :#{#dto.userId} is null)")
//    Page<RoomEntity> search(RoomDTO dto, Pageable pageable);

    @Query("select e from RoomEntity e" +
            " where 1=1" +
            " and (exists(select u from e.connectedUsers u" +
            "                   where u.id = :#{#userId})" +
            "       or :#{#userId} = e.adminId or :#{#userId} is null)" +
            " order by e.updatedAt ASC")
    List<RoomEntity> listRoomByUserId(Long userId);


//    @Query("select e.id from AccountEntity e" +
//            " join e.id" +
//            " where 1=1" +
//            " and (exists(select u from e.connectedUsers u" +
//            "                   where u.id = :#{#userId})" +
//            "       or :#{#userId} = e.adminId or :#{#userId} is null)")
//    List<Long> listConnectUserId(Long roomId);
}
