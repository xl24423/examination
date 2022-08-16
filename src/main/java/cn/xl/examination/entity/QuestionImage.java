package cn.xl.examination.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (QuestionImage)表实体类
 *
 * @author makejava
 * @since 2022-08-16 09:19:23
 */
@SuppressWarnings("serial")
@EqualsAndHashCode
@TableName("question_image")
@Accessors(chain = true)
@Data
public class QuestionImage extends Model<QuestionImage> {
    @TableField("question_id")
    private Integer questionId;
    @TableField("imageUrl")
    private String imageUrl;

    }

