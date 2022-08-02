package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Question;
import org.springframework.stereotype.Repository;

/**
 * (Question)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
@Repository
public interface QuestionDao extends BaseMapper<Question> {

}

