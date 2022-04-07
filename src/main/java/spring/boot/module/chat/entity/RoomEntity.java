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
@Table(name = "room")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class RoomEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean active;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "last_message_id")
    private Long lastMessageId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "last_message_id",updatable = false,insertable = false)
//    private MessageEntity lastMessage;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "admin_id", insertable = false, updatable = false)
//    private AccountEntity admin;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "room_user",
            joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<AccountEntity> connectedUsers;

    public void addUser(AccountEntity accountEntity) {
        connectedUsers.add(accountEntity);
    }

    public void removeUser(AccountEntity accountEntity) {
        connectedUsers.remove(accountEntity);
    }
}
