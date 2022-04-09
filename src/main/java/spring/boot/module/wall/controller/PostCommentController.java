package spring.boot.module.wall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.api.CoreController;
import spring.boot.module.wall.dto.PostCommentDTO;
import spring.boot.module.wall.entity.PostCommentEntity;
import spring.boot.module.wall.service.PostCommentService;

@RequestMapping("/post-comment")
@RestController
@CrossOrigin
public class PostCommentController extends CoreController<PostCommentDTO, PostCommentEntity> {
    @Autowired
    private PostCommentService service;
}
