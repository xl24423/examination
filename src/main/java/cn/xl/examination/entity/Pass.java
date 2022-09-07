package cn.xl.examination.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Pass)表实体类
 *
 * @author makejava
 * @since 2022-09-07 10:47:47
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("operation_ticket")
@Accessors(chain = true)
public class Pass extends Model<Pass> {
    //用户id
    @TableField("user_id")
    private Integer userId;
    //合格证书
    @TableField("qualify_url")
    private String qualifyUrl;

    }

