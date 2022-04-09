package spring.boot.module.wall.repository;


import org.springframework.stereotype.Repository;
import spring.boot.core.api.CoreRepository;
import spring.boot.module.wall.dto.PostDTO;
import spring.boot.module.wall.entity.PostEntity;

@Repository
public interface PostRepository extends CoreRepository<PostDTO, PostEntity> {
}