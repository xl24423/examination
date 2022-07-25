package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Answer;
import org.springframework.stereotype.Repository;

/**
 * (Answer)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:56
 */
@Repository
public interface AnswerDao extends BaseMapper<Answer> {

}

