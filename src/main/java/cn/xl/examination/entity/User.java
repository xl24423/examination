package cn.xl.examination.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-07-13 11:20:53
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("user")
@Accessors(chain = true)
public class User extends Model<User> {
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户名
    @TableField("username")
    private String username;
    //密码
    @TableField("password")
    private String password;
    //0女1男
    @TableField("gender")
    private Integer gender;
    //电话
    @TableField("tel")
    private String tel;
    //邀请码(管理员或者企业才有)
    @TableField("invite_code")
    private String inviteCode;
    //企业id, 对应company表
    @TableField("company_id")
    private Integer companyId;
    //电工证书保存地址
    @TableField("certificate_url")
    private String certificateUrl;
    //专业类别（多选）  1并网调度协议练习人员；2变电站运维人员；3停送电联系人员；
    @TableField("major_type")
    private String majorType;
    //审核 0通过1未通过
    @TableField("check")
    private Integer check;
    //真实姓名
    @TableField("name")
    private String name;
    //用户身份 管理员(1) 普通员工(10)
    @TableField("role_id")
    private Integer roleId;
    //用户创建时间
    @TableField("createtime")
    private LocalDateTime createtime;
    //用户是否封号 (1)可用 (0)封号
    @TableField("enable")
    private Integer enabled;
    //用户是否锁定 (1)可用 (0)锁定
    @TableField("locked")
    private Integer locked;
    }

