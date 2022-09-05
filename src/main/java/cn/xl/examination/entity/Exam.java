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
 * (Exam)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("exam")
@Accessors(chain = true)
public class Exam extends Model<Exam> {
    //id
    @TableField("bank_id")
    private Integer bankId;
    //开始时间
    @TableField("startdate")
    private LocalDateTime startdate;
    //结束时间
    @TableField("enddate")
    private LocalDateTime enddate;
    //用户名
    @TableField("username")
    private String username;
    //成绩
    @TableField("count")
    private Integer count;
    //试卷名称
    @TableField(exist = false)
    private String examName;
    //试卷总分
    @TableField(exist = false)
    private Integer sum;
    //用时 / (分)
    @TableField(exist = false)
    private Integer minute;

    }

