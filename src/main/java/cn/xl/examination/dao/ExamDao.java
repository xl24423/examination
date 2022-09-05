package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Exam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Exam)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Repository
public interface ExamDao extends BaseMapper<Exam> {
    @Select("select * from exam where username=#{username} and bank_id = #{bankId}")
    Integer isExam(String username, Integer bankId);
    @Delete("delete from exam where username = #{username} and bank_id=#{id}")
    void deleteByUsername(String username, Integer id);
    @Select("select * from exam where username = #{username}")
    List<Exam> myExam(String username);
    @Select("select * from exam where username = #{username} and bank_id = #{bankId}")
    Exam oneDetail(String username, Integer bankId);
    @Select("select * from exam")
    List<Exam> AllExam();
}

