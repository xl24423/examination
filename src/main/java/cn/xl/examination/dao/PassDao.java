package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Pass;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * (Pass)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-07 10:45:59
 */
@Repository
public interface PassDao extends BaseMapper<Pass> {
    @Select("select * from pass where user_id = #{id}")
    Pass getById(Integer id);
    @Insert("insert into pass values(#{id},'http://127.0.0.1:9090/static/pass.jpg')")
    Integer post(Integer id);
    @Delete("delete from pass where user_id = #{id}")
    Integer del(Integer id);
}

