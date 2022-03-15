package spring.boot.module.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.boot.core.dao.model.BaseEntity;
import spring.boot.module.auth.entity.AccountEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class RoomEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean active;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<AccountEntity> connectedUsers;

    public void addUser(AccountEntity accountEntity) {
        connectedUsers.add(accountEntity);
    }

    public void removeUser(AccountEntity accountEntity) {
        connectedUsers.remove(accountEntity);
    }
}
