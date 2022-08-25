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
 * (Answer)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:56
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("answer")
@Accessors(chain = true)
public class Answer extends Model<Answer> {
    //用户id
    @TableId(value = "id", type = IdType.AUTO)
    private String userId;
    //题目id
    @TableField("question_id")
    private Integer questionId;
    //用户回答
    @TableField("user_answer")
    private String userAnswer;
    //答题时间
    @TableField("createtime")
    private LocalDateTime createtime;
    //修改答案时间
    @TableField("question_bank_id")
    private Integer questionBankId;

}

