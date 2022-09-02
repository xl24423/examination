package cn.xl.examination.service.impl;

import cn.xl.examination.vo.AnswerVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.AnswerDao;
import cn.xl.examination.entity.Answer;
import cn.xl.examination.service.AnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ejb.Local;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * (Answer)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Service("answerService")
public class AnswerServiceImpl extends ServiceImpl<AnswerDao, Answer> implements AnswerService {
    @Resource
    AnswerDao answerDao;
    @Override
    public Integer post(Integer userId, AnswerVO[] answerList, Integer bankId) {
        List<Answer> answers = new ArrayList<>();
        for (int i=0;i<answerList.length;i++){
            if (answerList[i].getUser_answer().equals("")){
                answerList[i].setUser_answer("null");
            }
            Answer answer = new Answer();
            answer.setUserId(String.valueOf(userId));
            answer.setUserAnswer(answerList[i].getUser_answer());
            answer.setQuestionId(String.valueOf(answerList[i].getQid()));
            answer.setQuestionBankId(String.valueOf(bankId));
            answer.setCreatetime(LocalDateTime.now());
            answers.add(answer);
        }
        return answerDao.insertBatch(answers);
    }

    @Override
    public Answer[] countScore(Integer id, Integer bankId) {
        return answerDao.countScore(id,bankId);
    }
}

