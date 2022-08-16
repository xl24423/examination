package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.AnswerDao;
import cn.xl.examination.entity.Answer;
import cn.xl.examination.service.AnswerService;
import org.springframework.stereotype.Service;

/**
 * (Answer)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Service("answerService")
public class AnswerServiceImpl extends ServiceImpl<AnswerDao, Answer> implements AnswerService {

}

