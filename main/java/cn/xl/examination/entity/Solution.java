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
import java.time.LocalDateTime;

/**
 * (Solution)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("solution")
@Accessors(chain = true)
public class Solution extends Model<Solution> {
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //试卷编号
    @TableField("eid")
    private Integer eid;
    //问题
    @TableField("content")
    private String content;
    //答案
    @TableField("answer")
    private String answer;
    //选项A
    @TableField("A")
    private String a;
    //选项B
    @TableField("B")
    private String b;
    //选项C
    @TableField("C")
    private String c;
    //选项D
    @TableField("D")
    private String d;
    @TableField("createtime")
    private LocalDateTime createtime;
    @TableField("updatetime")
    private LocalDateTime updatetime;
    }

