package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.ScoreDao;
import cn.xl.examination.entity.Score;
import cn.xl.examination.service.ScoreService;
import org.springframework.stereotype.Service;

/**
 * (Score)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Service("scoreService")
public class ScoreServiceImpl extends ServiceImpl<ScoreDao, Score> implements ScoreService {

}

