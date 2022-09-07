package cn.xl.examination.service;

import cn.xl.examination.entity.User;
import cn.xl.examination.vo.AnswerVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.Exam;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;
import java.util.List;


/**
 * (Exam)表服务接口
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
public interface ExamService extends IService<Exam> {

    Boolean IsExam(String username, Integer bankId);

    Integer post(Integer bankId, String username, LocalDateTime starTime, Integer count);

    void delete(String username, Integer id);

    PageInfo<Exam> myExam(User user, Integer pageNum, Integer pageSize);

    Exam oneDetail(String username, Integer bankId);

    Boolean passCheck(String username);
}

