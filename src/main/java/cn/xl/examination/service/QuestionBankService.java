package cn.xl.examination.service;

import cn.xl.examination.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.QuestionBank;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * (QuestionBank)表服务接口
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
public interface QuestionBankService extends IService<QuestionBank> {

    PageInfo<QuestionBank> getAllQuestionBank(Integer pageNum, Integer pageSize);

    Integer deleteOne(Integer id);

    List<QuestionBank> getAllQuestionBankDontPage();

    void actExamination(String action, Integer id);

    PageInfo<QuestionBank> actionExam(Integer pageNum, Integer pageSize);

    void addQuestionBank(String username, String title, String time);

    QuestionBank selectQuestionBankByName(String title);

    QuestionBank selectOne(Integer id);

    String getTime(Integer id);
}

