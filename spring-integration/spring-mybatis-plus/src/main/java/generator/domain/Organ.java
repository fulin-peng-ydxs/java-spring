package generator.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName organ
 */
@TableName(value ="organ")
@Data
public class Organ implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String orgName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}