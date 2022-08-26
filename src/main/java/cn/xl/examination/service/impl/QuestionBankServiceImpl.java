package cn.xl.examination.service.impl;

import cn.xl.examination.dao.QuestionDao;
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

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Override
    public void actExamination(String action, Integer id) {
        questionBankDao.actExamination(action, id);
    }

    @Resource
    QuestionDao questionDao;

    @Override
    public PageInfo<QuestionBank> actionExam(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<QuestionBank> questionBanks = questionBankDao.findAllQuestionBank();
        for (QuestionBank q : questionBanks){
             Integer count = questionDao.countScore(q.getId());
             if (count == null){
                count = 0;
             }
            q.setCount(String.valueOf(count));
            Integer pass = count * 6 / 10;
            q.setPass(String.valueOf(pass));
        }
        PageInfo<QuestionBank> pageInfo = new PageInfo<>(questionBanks);
        return pageInfo;
    }

    @Override
    public void addQuestionBank(String username, String title, String time) {
        questionBankDao.addQuestionBank(username,title,time, LocalDateTime.now());
    }

    @Override
    public QuestionBank selectQuestionBankByName(String title) {
        return questionBankDao.selectQuestionBankByName(title);
    }

    @Override
    public QuestionBank selectOne(Integer id) {
        QuestionBank questionBank =  questionBankDao.selectById(id);
        Integer count = questionDao.countScore(questionBank.getId());
        if (count == null){
            count = 0 ;
        }
        questionBank.setCount(String.valueOf(count));
        questionBank.setPass(String.valueOf(count*6/10));
        return questionBank;
    }
}

