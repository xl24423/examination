package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Resources;
import org.apache.ibatis.annotations.Insert;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

/**
 * (Resources)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Repository
public interface ResourcesDao extends BaseMapper<Resources> {
    @Insert("insert into resources values(null,#{address},#{name},#{content});")
    Integer insertOne(String address, String name, String content);
}

