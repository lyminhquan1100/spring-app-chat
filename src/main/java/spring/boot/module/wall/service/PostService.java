package spring.boot.module.wall.service;

import spring.boot.core.api.CoreService;
import spring.boot.module.wall.dto.PostDTO;
import spring.boot.module.wall.entity.PostEntity;

public interface PostService extends CoreService<PostDTO, PostEntity> {
}