package spring.boot.module.wall.service;

import spring.boot.core.api.CoreService;
import spring.boot.module.wall.dto.PostCommentDTO;
import spring.boot.module.wall.entity.PostCommentEntity;

public interface PostCommentService extends CoreService<PostCommentDTO, PostCommentEntity> {
}