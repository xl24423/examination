package cn.xl.examination.entity;

import java.time.LocalDateTime;
import java.util.Date;

import cn.xl.examination.vo.OptionVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.sql.In;

import java.io.Serializable;
import java.util.List;

/**
 * (Question)表实体类
 *
 * @author makejava
 * @since 2022-08-15 10:20:34
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
    //回答A
    @TableField("A")
    private String A;
    //回答B
    @TableField("B")
    private String B;
    //回答C
    @TableField("C")
    private String C;
    //回答D
    @TableField("D")
    private String D;
    //题库外键
    @TableField("questionBankId")
    private String questionBankId;
    //题目解析
    @TableField("questionAnalysis")
    private String questionAnalysis;
    //创建时间
    @TableField("createtime")
    private LocalDateTime createtime;
    //单选或者多选
    @TableField("type")
    private String type;
    //得分
    @TableField("score")
    private Integer score;
    //出题人id
    @TableField("userId")
    private String userId;
    @TableField("solution")
    private String solution;
    @TableField(exist = false)
    private String url;
    @TableField(exist = false)
    private String userAnswer;
    }

