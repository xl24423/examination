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
 * (Resources)表实体类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode
@TableName("resources")
@Accessors(chain = true)
public class Resources extends Model<Resources> {
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //保存地址
    @TableField("address")
    private String address;
    //0视频1资料
    @TableField("type")
    private Integer type;
    }

