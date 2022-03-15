package spring.boot.module.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.core.dao.repository.BaseRepository;
import spring.boot.module.chat.dto.MessageDTO;
import spring.boot.module.chat.entity.MessageEntity;

import java.util.List;

@Repository
public interface MessageRepository extends BaseRepository<MessageEntity, MessageDTO, Long> {
    @Override
    @Query("select e from MessageEntity e" +
            " where (lower(e.content) like %:#{#dto.content}% or :#{#dto.content} is null)" +
            " and (e.toUser = :#{#dto.toUser} or :#{#dto.toUser} is null) " +
            " and (e.roomId = :#{#dto.roomId} or :#{#dto.roomId} is null) " +
            " and (e.createdBy = :#{#dto.createdBy} or :#{#dto.createdBy} is null) ")
    Page<MessageEntity> search(MessageDTO dto, Pageable pageable);

    List<MessageEntity> findAllByCreatedByAndRoomId(Long createdBy,Long roomId);
}
