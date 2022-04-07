package spring.boot.module.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import spring.boot.core.api.CoreEntity;
import spring.boot.module.auth.entity.AccountEntity;

@Entity
@Table(name = "react")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class EmojiEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "user_id")
    private Long userId;

    private Short type;

    @ManyToOne
    @JoinColumn(name = "message_id",updatable = false,insertable = false)
    private MessageEntity messageEntity;

    @ManyToOne
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private AccountEntity userEntity;
}