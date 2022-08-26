package cn.xl.examination.service.impl;

import cn.xl.examination.dao.*;
import cn.xl.examination.entity.QuestionImage;
import cn.xl.examination.entity.User;
import cn.xl.examination.exception.ServiceException;
import cn.xl.examination.service.QuestionBankService;
import cn.xl.examination.vo.TableDataVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.entity.Question;
import cn.xl.examination.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * (Question)表服务实现类
 *
 * @author makejava
 * @since 2022-08-12 16:25:31
 */
@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, Question> implements QuestionService {
    @Resource
    QuestionDao questionDao;

    @Override
    @Transactional
    public Integer addQuestion(String questionContent, TableDataVO[] myTable, String bankId, String analysis, String score, String type, String userId) {
        StringBuilder answer = new StringBuilder();
        String A = "";
        String B = "";
        String C = "";
        String D = "";
        String s = "";
        if(myTable.length>1){
            for (TableDataVO data : myTable) {
                if (data.getType() != null) {
                    answer.append(",").append(data.getOption());
                }
                if (data.getOption().equals("A")) {
                    A = data.getContent();
                }
                if (data.getOption().equals("B")) {
                    B = data.getContent();
                }
                if (data.getOption().equals("C")) {
                    C = data.getContent();
                }
                if (data.getOption().equals("D")) {
                    D = data.getContent();
                }
                s = answer.toString();
                s = s.substring(1);
            }
        }else{
            answer.append(myTable[0].getContent());
            s = answer.toString();
        }
        Question question = new Question().
                setQuestion(questionContent).
                setQuestionAnalysis(analysis).
                setQuestionBankId(bankId).
                setCreatetime(LocalDateTime.now()).
                setA(A).
                setB(B).
                setC(C).
                setD(D).
                setScore(Integer.valueOf(score)).
                setSolution(s).
                setUserId(userId).
                setType(type);
        int max = questionDao.selectMaxId();
        Integer i = questionDao.addQuestion(max+1,question.getQuestion(),
                question.getA(),
                question.getB(),
                question.getC(),
                question.getD(),
                question.getQuestionBankId(),
                question.getQuestionAnalysis(),
                question.getCreatetime(),
                question.getType(),
                question.getScore(),
                question.getUserId(),
                question.getSolution());
        if (i!=1){
            throw new ServiceException("数据库异常,请联系管理员");
        }
                return max+1;
    }

    @Resource
    QuestionImageDao questionImageDao;
    @Resource
    UserDao userDao;
    @Override
    public Integer postResource(QuestionImage questionImage) {
        return questionImageDao.addQuestionImage(questionImage.getQuestionId(),questionImage.getImageUrl());
    }

    @Resource
    QuestionBankDao questionBankDao;
    @Override
    public PageInfo<Question> getAllQuestions(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions = questionDao.selectAllQuestions();
        for (Question q : questions){
            switch (q.getType()) {
                case "1":
                    q.setType("单选题");
                    break;
                case "2":
                    q.setType("多选题");
                    break;
                case "3":
                    q.setType("判断题");
                    break;
            }
            String questionName = questionBankDao.backQuestionBankName(q.getQuestionBankId());
            q.setQuestionBankId(questionName);
            String username = userDao.backUserName(q.getUserId());
            q.setUserId(username);
            String url = questionImageDao.selectUrl(q.getId());
            q.setUrl(url);
        }
        return new PageInfo<>(questions);
    }

    @Override
    public Integer deleteByQuestionBankId(Integer id) {
        return questionDao.deleteByQuestionBankId(id);
    }

    @Override
    public List<Question> backExamQuestions(Integer id) {
        return questionDao.backExamQuestions(id);
    }
}

