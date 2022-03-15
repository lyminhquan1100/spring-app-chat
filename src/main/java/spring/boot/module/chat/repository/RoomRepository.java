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
public interface RoomRepository extends BaseRepository<RoomEntity, RoomDTO,Long> {
    @Override
    @Query("select e from RoomEntity e" +
            " where (e.idRes = :#{#dto.idRes} or :#{#dto.idRes} is null)" +
            " and (exists(select u from e.connectedUsers u" +
            "                   where u.id = :#{#dto.userId})" +
            "       or :#{#dto.userId} = e.idLeader or :#{#dto.userId} is null)")
    Page<RoomEntity> search(RoomDTO dto, Pageable pageable);

    List<RoomEntity> findByIdRes(Long idRes);
}
