package spring.boot.module.wall.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot.core.api.CoreServiceImpl;
import spring.boot.module.auth.entity.AccountEntity;
import spring.boot.module.auth.repository.AccountRepository;
import spring.boot.module.wall.repository.PostCommentRepository;
import spring.boot.module.wall.dto.PostCommentDTO;
import spring.boot.module.wall.entity.PostCommentEntity;

@Service
public class PostCommentServiceImpl
        extends CoreServiceImpl<PostCommentDTO, PostCommentEntity>
        implements PostCommentService {

    @Autowired
    private PostCommentRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public PostCommentDTO mapToDTO(PostCommentEntity entity) {
        PostCommentDTO dto = super.mapToDTO(entity);
        AccountEntity accountEntity = accountRepository.findById(entity.getCreatedBy()).orElse(null);
        if(accountEntity != null){
            dto.setFullName(accountEntity.getFullName());
            dto.setAvatar(accountEntity.getAvatar());
        }
        return dto;
    }
}