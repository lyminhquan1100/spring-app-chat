package spring.boot.module.wall.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.auth.service.AccountService;
import spring.boot.module.wall.dto.PostDTO;
import spring.boot.module.wall.entity.PostEmojiEntity;
import spring.boot.module.wall.entity.PostEntity;
import spring.boot.module.wall.repository.PostCommentRepository;
import spring.boot.module.wall.repository.PostEmojiRepository;
import spring.boot.module.wall.repository.PostRepository;

@Service
public class PostServiceImpl
        extends CoreServiceImpl<PostDTO, PostEntity>
        implements PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostEmojiRepository postEmojiRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Override
    public PostDTO mapToDTO(PostEntity entity) {
        PostDTO dto =super.mapToDTO(entity);

        AccountEntity accountEntity = accountRepository.findById(entity.getCreatedBy()).orElse(null);
        if (accountEntity != null) {
            dto.setAuthor(accountEntity.getFullName());
            dto.setAvatar(accountEntity.getAvatar());
        }

        dto.setNumberLike(postEmojiRepository.countByPostId(entity.getId()));
        dto.setNumberComment(postCommentRepository.countByPostId(entity.getId()));

        Long currentUserId = accountService.getCurrentUserId();
        dto.setIsLike(postEmojiRepository.existsByCreatedByAndPostId(currentUserId, entity.getId()));

        PostEmojiEntity likeEntity = postEmojiRepository.findByCreatedByAndPostId(currentUserId, entity.getId());
        if(likeEntity != null){
            dto.setLikeId(likeEntity.getId());
        }
        dto.setIsLike(likeEntity != null);

        return dto;
    }
}