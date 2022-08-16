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
 * (OperationTicket)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("operation_ticket")
@Accessors(chain = true)
public class OperationTicket extends Model<OperationTicket> {
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //用户id
    @TableField("user_id")
    private Integer userId;
    //图片存储位置
    @TableField("picUrl")
    private String picUrl;
    //下发时间
    @TableField("createAt")
    private LocalDateTime createat;

    }

