package spring.boot.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

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
//  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private ZonedDateTime createdAt;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private ZonedDateTime updatedAt;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long createdBy;

  @JsonInclude(Include.NON_NULL)
  @ApiModelProperty(hidden = true)
  private Long updatedBy;

  public void setNull(){
    this.createdAt = null;
    this.createdBy = null;
    this.updatedAt = null;
    this.updatedBy = null;

  }
}
