package spring.boot.module.wall.repository;


import org.springframework.stereotype.Repository;
import spring.boot.core.api.CoreRepository;
import spring.boot.module.wall.dto.PostEmojiDTO;
import spring.boot.module.wall.entity.PostEmojiEntity;

@Repository
public interface PostEmojiRepository extends CoreRepository<PostEmojiDTO, PostEmojiEntity> {
    Long countByPostId(Long id);

    Boolean existsByCreatedByAndPostId(Long userId, Long postId);

    PostEmojiEntity findByCreatedByAndPostId(Long userId, Long postId);
}