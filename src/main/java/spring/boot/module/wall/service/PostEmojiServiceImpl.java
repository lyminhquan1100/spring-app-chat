package spring.boot.module.wall.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.core.exception.BaseException;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.wall.dto.PostEmojiDTO;
import spring.boot.module.wall.entity.PostEmojiEntity;
import spring.boot.module.wall.repository.PostEmojiRepository;

@Service
public class PostEmojiServiceImpl
        extends CoreServiceImpl<PostEmojiDTO, PostEmojiEntity>
        implements PostEmojiService {

    @Autowired
    private PostEmojiRepository repository;

    @Autowired
    private AccountService accountService;

    @Override
    protected void beforeSave(PostEmojiEntity entity, PostEmojiDTO dto) {
        if(repository.existsByCreatedByAndPostId(accountService.getCurrentUserId(), dto.getPostId())){
            throw new BaseException("Đã like");
        }
        super.beforeSave(entity, dto);
    }
}