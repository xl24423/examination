package cn.xl.examination.service;

import cn.xl.examination.entity.QuestionImage;
import cn.xl.examination.vo.TableDataVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.Question;
import org.springframework.data.relational.core.sql.In;

/**
 * (Question)表服务接口
 *
 * @author makejava
 * @since 2022-08-12 16:25:31
 */
public interface QuestionService extends IService<Question> {

    Integer addQuestion(String questionContent, TableDataVO[] myTable, Integer bankId, String analysis, String score, String type, Integer userId);

    Integer postResource(QuestionImage questionImage);
}

