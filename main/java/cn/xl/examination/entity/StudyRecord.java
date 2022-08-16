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
 * (StudyRecord)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("study_record")
@Accessors(chain = true)
public class StudyRecord extends Model<StudyRecord> {
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户id
    @TableField("user_id")
    private Integer userId;
    //资源类型 0视频1资料
    @TableField("resource_type")
    private Integer resourceType;
    //播放开始时间
    @TableField("strattime")
    private LocalDateTime startTime;
    // 播放结束时间
    @TableField("endtime")
    private LocalDateTime endTime;

    }

