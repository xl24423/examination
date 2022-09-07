package cn.xl.examination.service.impl;

import cn.xl.examination.dao.*;
import cn.xl.examination.entity.QuestionBank;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
        Arrays.sort(myTable);
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
            }
            s = answer.toString();
            s = s.substring(1);
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
        return convey(questions);
    }

    @Override
    public Integer deleteByQuestionBankId(Integer id) {
        return questionDao.deleteByQuestionBankId(id);
    }

    @Override
    public Question[] backExamQuestions(Integer id) {
        return questionDao.backExamQuestions(id);
    }

    @Override
    public Question[] countScore(Integer bankId) {
        return questionDao.selectByQuestionBankId(bankId);
    }

    @Override
    public Question[] backQuestionsDetails(Integer id) {
        return questionDao.backQuestionsDetails(id);
    }

    @Override
    public PageInfo<Question> searchAllQuestions(String questionName, String region, String questionContext, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions = new ArrayList<>();
        if (!questionName.equals("")){
            List<QuestionBank> banks = questionBankDao.nameBlurSearch(questionName);
            for (QuestionBank b : banks){
                questions.addAll(Arrays.asList(questionDao.selectByQuestionBankId(b.getId())));
            }
        }else if (!region.equals("")){
            questions = questionDao.selectByRegion(region);
        }else if (!questionContext.equals("")){
            questions = questionDao.selectByQuestionContext(questionContext);
        }
        return convey(questions);
    }

    @Override
    public Question searchOne(Integer id) {
       Question question = questionDao.selectById(id);
       question.setUrl(questionImageDao.selectUrl(question.getId()));
       return question;
    }

    @Override
    public void deleteByQuestionId(int id) {
        questionDao.deleteByQuestionId(id);
    }

    private PageInfo<Question> convey(List<Question> questions){
        List<QuestionBank> allQuestionBank = questionBankDao.findAllQuestionBank();
        List<QuestionImage> allQuestionImage = questionImageDao.selectList(null);
        List<User> allUsers = userDao.findAllAdmin();
        Map<Integer,String> QuestionBankMap = new ConcurrentHashMap<>();
        Map<Integer,String> QuestionImageMap = new ConcurrentHashMap<>();
        Map<Integer,String> UserMap = new ConcurrentHashMap<>();
        for (QuestionBank qb : allQuestionBank){
            QuestionBankMap.put(qb.getId(),qb.getName());
        }
        for (QuestionImage qi : allQuestionImage){
            QuestionImageMap.put(qi.getQuestionId(),qi.getImageUrl());
        }
        for (User u : allUsers){
            UserMap.put(u.getId(),u.getName());
        }
        for (Question q : questions){
            q.setQuestionBankId(QuestionBankMap.get(Integer.parseInt(q.getQuestionBankId())));
            q.setUrl(QuestionImageMap.get(q.getId()));
            q.setUserId(UserMap.get(Integer.parseInt(q.getUserId())));
        }
        return new PageInfo<>(questions);
    }
}

