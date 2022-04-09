package spring.boot.module.wall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.core.api.CoreController;
import spring.boot.module.wall.dto.PostEmojiDTO;
import spring.boot.module.wall.entity.PostEmojiEntity;
import spring.boot.module.wall.service.PostEmojiService;

@RequestMapping("/post-emoji")
@RestController
@CrossOrigin
public class PostEmojiController extends CoreController<PostEmojiDTO, PostEmojiEntity> {
    @Autowired
    private PostEmojiService service;
}
