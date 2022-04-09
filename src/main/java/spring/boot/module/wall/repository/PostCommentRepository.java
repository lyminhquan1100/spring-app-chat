package spring.boot.module.wall.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.core.api.CoreRepository;
import spring.boot.module.wall.dto.PostCommentDTO;
import spring.boot.module.wall.entity.PostCommentEntity;

@Repository
public interface PostCommentRepository extends CoreRepository<PostCommentDTO, PostCommentEntity> {
    Long countByPostId(Long id);

    @Override
    @Query("select e from PostCommentEntity e" +
            " where (lower(e.content) like %:#{#dto.content}% or :#{#dto.content} is null)" +
            " and (e.postId = :#{#dto.postId} or :#{#dto.postId} is null)")
    Page<PostCommentEntity> search(PostCommentDTO dto, Pageable pageable);

}