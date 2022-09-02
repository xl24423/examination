package cn.xl.examination.service.impl;

import cn.xl.examination.vo.AnswerVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.ExamDao;
import cn.xl.examination.entity.Exam;
import cn.xl.examination.service.ExamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
        exam.setBank_id(bankId);
        exam.setStartdate(starTime);
        exam.setEnddate(LocalDateTime.now());
        exam.setCount(count);
        exam.setUsername(username);
        return examDao.insert(exam);
    }
}

