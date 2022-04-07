package spring.boot.module.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.boot.module.chat.enums.PackEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackDTO {
    PackEnum type;

    Object payload;
}
