package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Question;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;
import java.util.List;

/**
 * (Question)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-12 16:25:31
 */
public interface QuestionDao extends BaseMapper<Question> {
    @Insert("insert into question values(#{id},#{question},#{A},#{B},#{C},#{D},#{questionBankId},#{questionAnalysis},#{createtime},#{type},#{score},#{userId},#{solution})")
    Integer addQuestion(Integer id, String question, String A, String B, String C, String D, String questionBankId,
                        String questionAnalysis, LocalDateTime createtime, String type,
                        Integer score, String userId,String solution
                        );
    @Select("select max(id) from question")
    int selectMaxId();
    @Select("select * from question")
    List<Question> selectAllQuestions();
    @Select("select sum(score) from question q left join question_bank qb on q.question_bank_id = qb.id where\n" +
            "qb.id = #{id}")
    Integer countScore(Integer id);
    @Delete("delete from question where question_bank_id = #{id}")
    Integer deleteByQuestionBankId(Integer id);
    @Select("select q.id,q.question,q.a,q.b,q.c,q.d,q.type from question q left join question_bank qb on q.question_bank_id = qb.id where\n" +
            "qb.id = #{id}")
    Question[] backExamQuestions(Integer id);
    @Select("select * from question where question_bank_id = #{bankId}")
    Question[] selectByQuestionBankId(Integer bankId);
    @Select("select * from question q left join question_bank qb on q.question_bank_id = qb.id where qb.id = #{id}")
    Question[] backQuestionsDetails(Integer id);
    @Select("select * from question where type = #{region}")
    List<Question> selectByRegion(String region);
    @Select("select * from question where question like CONCAT('%',#{questionContext},'%')")
    List<Question> selectByQuestionContext(String questionContext);
}

