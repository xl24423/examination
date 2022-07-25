package cn.xl.examination.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (Role)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("role")
@Accessors(chain = true)
public class Role extends Model<Role> {
    @TableField("id")
    private Integer id;
    //角色名称
    @TableField("name")
    private String name;
    }

