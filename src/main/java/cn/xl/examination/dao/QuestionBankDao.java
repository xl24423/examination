package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.QuestionBank;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
}

