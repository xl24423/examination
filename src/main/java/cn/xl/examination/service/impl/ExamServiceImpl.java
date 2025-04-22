package cn.xl.examination.service.impl;

import cn.xl.examination.dao.QuestionBankDao;
import cn.xl.examination.dao.QuestionDao;
import cn.xl.examination.entity.Question;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public Integer post(Integer bankId,String username, LocalDateTime starTime,Float count) {
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
            float sum = questionDao.countScore(questionBank.getId());
            a.setExamName(questionBank.getName());
            a.setSum(sum);
        }
        PageInfo<Exam> pageInfo = new PageInfo<>(exams);
        return pageInfo;
    }
    @Override
    public PageInfo<Exam> myPassExam(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Exam> exams;
        if (user.getRoleId().equals("1")){
            exams = examDao.AllExam();
        }else{
            exams = examDao.myExam(user.getUsername());
        }
        Iterator<Exam> iterator = exams.iterator();
        while (iterator.hasNext()) {
            Exam a = iterator.next();
            QuestionBank questionBank = questionBankDao.selectById(a.getBankId()); // 查数据库
            float sum = questionDao.countScore(questionBank.getId()); // 查数据库
            float count = a.getCount();
            if (count / sum < 0.6) {
                iterator.remove(); // 删除不符合条件的
            } else {
                a.setExamName(questionBank.getName()); // 只修改符合条件的
                a.setSum(sum);
            }
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

    @Override
    public Boolean passCheck(String username) {
        boolean isPass = true;
        List<Exam> exams = examDao.myExam(username);
        List<QuestionBank> questionBanks = questionBankDao.findAllUserQuestionBank();
        if (exams.size() < questionBanks.size()){
            return false;
        }
        Map<Integer, Float> countMap = new ConcurrentHashMap<>();
        for (QuestionBank qb : questionBanks){
            Float countScore = questionDao.countScore(qb.getId());
            if (countScore==null){
                countScore = 0.0F;
            }
            countMap.put(qb.getId(), countScore*6/10);
        }
        for (Exam e : exams){
            if (e.getCount() < countMap.get(e.getBankId())){
                isPass = false;
            }
        }

        return isPass;
    }


}

