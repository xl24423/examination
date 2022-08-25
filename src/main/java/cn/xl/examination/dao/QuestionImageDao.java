package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.QuestionImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * (QuestionImage)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-16 09:21:30
 */
public interface QuestionImageDao extends BaseMapper<QuestionImage> {
    @Insert("insert into question_image values(#{questionId},#{imageUrl}) ")
    Integer addQuestionImage(Integer questionId, String imageUrl);
    @Select("select image_url from question_image where question_id = #{id}")
    String selectUrl(Integer id);
}

