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
 * (Question)表实体类
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
@SuppressWarnings("serial")
@TableName("question")
@EqualsAndHashCode
@Accessors(chain = true)
@Data
public class Question extends Model<Question> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //问题
    @TableField("question")
    private String question;
    //问题解析
    @TableField("resolution")
    private String resolution;
    //回答A
    @TableField("a")
    private String a;
    //回答B
    @TableField("b")
    private String b;
    //回答C
    @TableField("c")
    private String c;
    //回答D
    @TableField("d")
    private String d;
    //类型(1:单选,2:多选,3:判断)
    @TableField("type")
    private Integer type;
    //判断
    @TableField("judge")
    private String judge;
    //题库外键
    @TableField("question_bank_id")
    private Integer questionBankId;
}

