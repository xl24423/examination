package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.ExamDao;
import cn.xl.examination.entity.Exam;
import cn.xl.examination.service.ExamService;
import org.springframework.stereotype.Service;

/**
 * (Exam)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Service("examService")
public class ExamServiceImpl extends ServiceImpl<ExamDao, Exam> implements ExamService {

}

