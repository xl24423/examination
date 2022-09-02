package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Exam;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
}

