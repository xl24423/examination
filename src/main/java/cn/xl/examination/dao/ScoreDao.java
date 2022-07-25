package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Score;
import org.springframework.stereotype.Repository;

/**
 * (Score)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Repository
public interface ScoreDao extends BaseMapper<Score> {

}

