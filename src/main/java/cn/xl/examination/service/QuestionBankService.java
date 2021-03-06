package cn.xl.examination.service;

import cn.xl.examination.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.QuestionBank;
import com.github.pagehelper.PageInfo;

/**
 * (QuestionBank)表服务接口
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
public interface QuestionBankService extends IService<QuestionBank> {

    PageInfo<QuestionBank> getAllQuestionBank(Integer pageNum, Integer pageSize);

    Integer deleteOne(Integer id);
}

