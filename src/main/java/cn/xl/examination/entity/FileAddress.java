package cn.xl.examination.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@TableName("file_address")
@Accessors(chain = true)
public class FileAddress {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("file_url")
    private String fileUrl;
    //邀请码
    @TableField("file_name")
    private String fileName;
    //创建时间
    @TableField("content")
    private String content;
    @TableField("objectKey")
    private String objectKey;
}
