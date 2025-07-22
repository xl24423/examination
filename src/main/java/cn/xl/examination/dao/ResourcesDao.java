package cn.xl.examination.dao;

import cn.xl.examination.entity.FileAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Resources;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Select("select * from resources")
    List<Resources> findAllResource();

    @Delete("delete from resources where id = #{id}")
    Integer deleteByResourceId(Integer id);
}

