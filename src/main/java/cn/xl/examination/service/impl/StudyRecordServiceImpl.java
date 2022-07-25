package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.StudyRecordDao;
import cn.xl.examination.entity.StudyRecord;
import cn.xl.examination.service.StudyRecordService;
import org.springframework.stereotype.Service;

/**
 * (StudyRecord)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Service("studyRecordService")
public class StudyRecordServiceImpl extends ServiceImpl<StudyRecordDao, StudyRecord> implements StudyRecordService {

}

