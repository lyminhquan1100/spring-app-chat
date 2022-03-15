package spring.boot.module.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.boot.core.dao.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class MessageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long toUser;

    private String content;

    private Long roomId;
}
