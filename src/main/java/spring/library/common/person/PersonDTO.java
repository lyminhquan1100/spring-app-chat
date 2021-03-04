package spring.library.common.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.library.common.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO extends BaseDTO {
  private Long id;
  private String name;
}
