package spring.library.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long id;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private String createdAt;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private String updatedAt;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long createdBy;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long updatedBy;
}
