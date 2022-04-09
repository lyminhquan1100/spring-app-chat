package spring.boot.module.wall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import spring.boot.core.api.CoreEntity;

@Entity
@Table(name = "tb_post_comment")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class PostCommentEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    @Column(length = 2000)
    private String content;
}