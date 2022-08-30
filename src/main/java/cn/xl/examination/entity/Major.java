package cn.xl.examination.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Major)表实体类
 *
 * @author makejava
 * @since 2022-08-30 11:18:34
 */
@SuppressWarnings("serial")
public class Major extends Model<Major> {
    
    private Integer id;
    //专业
    private String major;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    }

