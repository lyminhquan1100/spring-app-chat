package spring.boot.module.wall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import spring.boot.core.api.CoreEntity;

@Entity
@Table(name = "tb_post")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class PostEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String content;

    private String imgPath;
}