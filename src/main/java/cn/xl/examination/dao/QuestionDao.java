package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;

/**
 * (Question)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-12 16:25:31
 */
public interface QuestionDao extends BaseMapper<Question> {
    @Insert("insert into question values(#{id},#{question},#{A},#{B},#{C},#{D},#{questionBankId},#{questionAnalysis},#{createtime},#{type},#{score},#{userId},#{solution})")
    Integer addQuestion(Integer id, String question, String A, String B, String C, String D, Integer questionBankId,
                        String questionAnalysis, LocalDateTime createtime, Integer type,
                        Integer score, Integer userId,String solution
                        );
    @Select("select max(id) from question")
    int selectMaxId();
}

