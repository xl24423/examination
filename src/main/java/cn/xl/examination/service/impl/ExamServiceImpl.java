package cn.xl.examination.service.impl;

import cn.xl.examination.dao.QuestionBankDao;
import cn.xl.examination.dao.QuestionDao;
import cn.xl.examination.entity.QuestionBank;
import cn.xl.examination.entity.User;
import cn.xl.examination.exception.ServiceException;
import cn.xl.examination.service.QuestionService;
import cn.xl.examination.vo.AnswerVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.ExamDao;
import cn.xl.examination.entity.Exam;
import cn.xl.examination.service.ExamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * (Exam)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Service("examService")
public class ExamServiceImpl extends ServiceImpl<ExamDao, Exam> implements ExamService {
    @Resource
    ExamDao examDao;

    @Override
    public Boolean IsExam(String username, Integer bankId) {
        Integer i = examDao.isExam(username,bankId);
        return i==null;
    }

    @Override
    public Integer post(Integer bankId,String username, LocalDateTime starTime,Integer count) {
        Exam exam = new Exam();
        exam.setBankId(bankId);
        exam.setStartdate(starTime);
        exam.setEnddate(LocalDateTime.now());
        exam.setCount(count);
        exam.setUsername(username);
        return examDao.insert(exam);
    }

    @Override
    public void delete(String username, Integer id) {
        examDao.deleteByUsername(username,id);
    }
    @Resource
    QuestionBankDao questionBankDao;
    @Resource
    QuestionDao questionDao;
    @Override
    public PageInfo<Exam> myExam(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Exam> exams;
        if (user.getRoleId().equals("1")){
            exams = examDao.AllExam();
        }else{
            exams = examDao.myExam(user.getUsername());
        }
        for (Exam a : exams){
            QuestionBank questionBank = questionBankDao.selectById(a.getBankId());
            a.setExamName(questionBank.getName());
            a.setSum(questionDao.countScore(questionBank.getId()));
        }
        PageInfo<Exam> pageInfo = new PageInfo<>(exams);
        return pageInfo;
    }

    @Override
    public Exam oneDetail(String username, Integer bankId) {
        Exam exam = examDao.oneDetail(username, bankId);
        QuestionBank questionBank = questionBankDao.selectById(exam.getBankId());
        exam.setExamName(questionBank.getName());
        exam.setSum(questionDao.countScore(questionBank.getId()));
        long endTimestamp = exam.getEnddate().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long startTimestamp = exam.getStartdate().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        Integer minute = Math.toIntExact((endTimestamp - startTimestamp) / 1000 / 60);
        exam.setMinute(minute);
        return exam;
    }
}

