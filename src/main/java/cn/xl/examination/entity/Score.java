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
 * (Score)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("score")
@Accessors(chain = true)
public class Score extends Model<Score> {
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //试卷编号
    @TableField("eid")
    private Integer eid;
    //用户id
    @TableField("user_id")
    private Integer userId;
    //考试成绩
    @TableField("score")
    private Float score;
    //提交时间
    @TableField("sub_time")
    private LocalDateTime subTime;
    }

