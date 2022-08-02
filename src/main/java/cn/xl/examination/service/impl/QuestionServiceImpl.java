package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.QuestionDao;
import cn.xl.examination.entity.Question;
import cn.xl.examination.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * (Question)表服务实现类
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, Question> implements QuestionService {

}

