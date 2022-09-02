package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Answer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Answer)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:56
 */
@Repository
public interface AnswerDao extends BaseMapper<Answer> {
     Integer insertBatch(List<Answer> answers);
     @Select("select * from answer where user_id = #{id} and question_bank_id = #{bankId}")
     Answer[] countScore(Integer id, Integer bankId);
}

