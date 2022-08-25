package cn.xl.examination.entity;

import java.time.LocalDateTime;
import java.util.Date;


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
 * (Company)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("company")
@Accessors(chain = true)
public class Company extends Model<Company> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //公司名称
    @TableField("name")
    private String name;
    //邀请码
    @TableField("invite_code")
    private String inviteCode;
    //创建时间
    @TableField("createtime")
    private LocalDateTime createtime;

    }

