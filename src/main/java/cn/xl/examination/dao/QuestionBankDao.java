package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.QuestionBank;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * (QuestionBank)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
@Repository
public interface QuestionBankDao extends BaseMapper<QuestionBank> {
    @Select("select * from question_bank")
    List<QuestionBank> findAllQuestionBank();
    @Delete("delete from question_bank where id = #{id}")
    Integer deleteOne(Integer id);
    @Select("select name from question_bank where id = #{questionBankId}")
    String backQuestionBankName(String questionBankId);
    @Update("update question_bank set isAction=#{action} where id = #{id}")
    void actExamination(String action, Integer id);
    @Insert("insert into question_bank values(null,#{title},#{now},'false',#{time},#{username})")
    void addQuestionBank(String username, String title, String time, LocalDateTime now);
    @Select("select * from question_bank where name = #{title}")
    QuestionBank selectQuestionBankByName(String title);
    @Select("select * from question_bank where isAction = 'true'")
    List<QuestionBank> findAllUserQuestionBank();
    @Select("select time from question_bank where id=#{id}")
    String getTime(Integer id);
    @Select("select * from question_bank where name like CONCAT('%', #{name}, '%')")
    List<QuestionBank> likeName(String name);
    @Select("select * from question_bank where isAction = 'true' and name like CONCAT('%', #{name}, '%')")
    List<QuestionBank> actionExamSearch(String name);
    @Select("select * from question_bank where name like CONCAT('%', #{name}, '%')")
    List<QuestionBank> nameBlurSearch(String name);
}

