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
 * (RolePermission)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("role_permission")
@Accessors(chain = true)
public class RolePermission extends Model<RolePermission> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //角色id
    @TableField("role_id")
    private Integer roleId;
    //权限id
    @TableField("permission_id")
    private Integer permissionId;

    }

