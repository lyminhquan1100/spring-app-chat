package spring.library.common.person;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.library.common.dao.model.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person")
public class PersonEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
}
