package spring.boot.core.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoreDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(hidden = true)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(hidden = true)
//  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//  @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(hidden = true)
    private Long createdBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(hidden = true)
    private Long updatedBy;
}
