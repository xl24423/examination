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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //开始时间
    @TableField("startdate")
    private LocalDateTime startdate;
    //结束时间
    @TableField("enddate")
    private LocalDateTime enddate;

    }

