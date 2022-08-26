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
 * (QuestionBank)表实体类
 *
 * @author makejava
 * @since 2022-08-02 16:24:02
 */
@SuppressWarnings("serial")
@TableName("question_bank")
@EqualsAndHashCode
@Accessors(chain = true)
@Data
public class QuestionBank extends Model<QuestionBank> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //题库名称
    @TableField("name")
    private String name;
    //创建时间
    @TableField("createtime")
    private String createtime;
    //是否开始
    @TableField("isAction")
    private String isAction;
    // 考试时长
    @TableField("time")
    private String time;
    // 考试总分
    @TableField(exist = false)
    private String count;
    // 及格分数
    @TableField(exist = false)
    private String pass;
    // 出题人
    @TableField("admin")
    private String admin;



}

