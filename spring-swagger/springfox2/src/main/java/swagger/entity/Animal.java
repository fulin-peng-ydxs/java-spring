package swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动物类
 *
 * @author fulin-peng
 * 2024-07-15  10:57
 */
@Data
@ApiModel("动物模型")
public class Animal {
    @ApiModelProperty(value = "性别",required = true)
    private String sex;

    @ApiModelProperty(value = "年龄",required = true,example = "1")
    private Integer age;
    @ApiModelProperty(value = "颜色",required = true)
    private String color;
    @ApiModelProperty(value = "类型",required = true)
    private String type;
}
