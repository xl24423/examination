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
 * (Permission)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("permission")
@Accessors(chain = true)
public class Permission extends Model<Permission> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //权限url
    @TableField("name")
    private String name;
    //权限说明
    @TableField("desc")
    private String desc;
    }

