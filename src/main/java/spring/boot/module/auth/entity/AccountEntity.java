package spring.boot.module.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import spring.boot.core.api.CoreEntity;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Where(clause = "deleted=0")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity extends CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String avatar;

    /**
     * 1: admin
     * 2: user
     * 3: guest
     */
    private Long role;
}
