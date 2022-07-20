package spring.boot.module.wall.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.api.CoreController;
import spring.boot.module.wall.dto.PostDTO;
import spring.boot.module.wall.entity.PostEntity;
import spring.boot.module.wall.service.PostService;

@RequestMapping("/post")
@RestController
@CrossOrigin
public class PostController extends CoreController<PostDTO, PostEntity,PostService> {
    public PostController(PostService s) {
        super(s);
    }
}
