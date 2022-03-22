package spring.boot.module.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.core.dao.repository.BaseRepository;
import spring.boot.module.chat.dto.LastSeenDTO;
import spring.boot.module.chat.entity.LastSeenEntity;

@Repository
public interface LastSeenRepository extends BaseRepository<LastSeenEntity, LastSeenDTO, Long> {
    @Query("select e from LastSeenEntity e" +
            " join e.messageEntity m" +
            " where (m.roomId = :#{#roomId}) " +
            " and (e.userId = :#{#userId}) ")
    LastSeenEntity findLastSeenFromRoomId(Long userId,Long roomId);
}