package cn.xl.examination.service.impl;

import cn.xl.examination.entity.User;
import cn.xl.examination.exception.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.QuestionBankDao;
import cn.xl.examination.entity.QuestionBank;
import cn.xl.examination.service.QuestionBankService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (QuestionBank)表服务实现类
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
@Service("questionBankService")
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankDao, QuestionBank> implements QuestionBankService {
    @Autowired
    QuestionBankDao questionBankDao;
    @Override
    public PageInfo<QuestionBank> getAllQuestionBank(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QuestionBank> allQuestionBank = questionBankDao.findAllQuestionBank();
        log.debug("所有的题库"+allQuestionBank);
        PageInfo<QuestionBank> QuestionBankPageInfo = new PageInfo<>(allQuestionBank);
        return QuestionBankPageInfo;
    }

    @Override
    public Integer deleteOne(Integer id) {
        log.debug("删除的题库id"+id);
        return questionBankDao.deleteOne(id);
    }

    @Override
    public List<QuestionBank> getAllQuestionBankDontPage() {
        return questionBankDao.findAllQuestionBank();
    }
}

