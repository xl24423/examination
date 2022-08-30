package cn.xl.examination.dao;

import cn.xl.examination.entity.Major;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Major)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-30 11:18:32
 */
@Repository
public interface MajorDao extends BaseMapper<Major> {
    @Select("select * from major")
    List<Major> getAllMajor();
}

