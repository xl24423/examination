package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Solution;
import org.springframework.stereotype.Repository;

/**
 * (Solution)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Repository
public interface SolutionDao extends BaseMapper<Solution> {

}

