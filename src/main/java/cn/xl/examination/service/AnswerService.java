package cn.xl.examination.service;

import cn.xl.examination.vo.AnswerVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.Answer;
import org.springframework.stereotype.Repository;

/**
 * (Answer)表服务接口
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */

public interface AnswerService extends IService<Answer> {

    Integer post(Integer userId, AnswerVO[] answerList, Integer bankId);

    Answer[] countScore(Integer id, Integer bankId);
}

