package spring.boot.module.wall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import spring.boot.core.api.CoreEntity;

@Entity
@Table(name = "tb_post_emoji")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class PostEmojiEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
}