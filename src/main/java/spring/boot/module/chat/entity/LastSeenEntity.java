package spring.boot.module.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.boot.core.api.CoreEntity;
import spring.boot.module.auth.entity.AccountEntity;

import javax.persistence.*;

@Entity
@Table(name = "last_seen")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class LastSeenEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id",updatable = false,insertable = false)
    private MessageEntity messageEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private AccountEntity account;
}
