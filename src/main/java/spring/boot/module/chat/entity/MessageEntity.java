package spring.boot.module.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.boot.core.api.CoreEntity;
import spring.boot.module.auth.entity.AccountEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "message")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class MessageEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_id")
    private Long fromId;

//    private Long toUser;

    @Column(length = 1000)
    private String content;

    private Short type;

    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "from_id",insertable = false,updatable = false)
    private AccountEntity fromUser;

    @OneToMany(mappedBy = "messageEntity")
//    @JoinColumn(name = "from_id",insertable = false,updatable = false)
    private List<LastSeenEntity> listLastSeen;

    @OneToMany(mappedBy = "messageEntity")
    private List<EmojiEntity> listEmoji;
}
