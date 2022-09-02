package cn.xl.examination.service;

import cn.xl.examination.entity.QuestionImage;
import cn.xl.examination.vo.TableDataVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.Question;
import com.github.pagehelper.PageInfo;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

/**
 * (Question)表服务接口
 *
 * @author makejava
 * @since 2022-08-12 16:25:31
 */
public interface QuestionService extends IService<Question> {

    Integer addQuestion(String questionContent, TableDataVO[] myTable, String bankId, String analysis, String score, String type, String userId);

    Integer postResource(QuestionImage questionImage);

    PageInfo<Question> getAllQuestions(Integer pageNum,Integer pageSize);

    Integer deleteByQuestionBankId(Integer id);

    Question[] backExamQuestions(Integer id);

    Question[] countScore(Integer bankId);
}

