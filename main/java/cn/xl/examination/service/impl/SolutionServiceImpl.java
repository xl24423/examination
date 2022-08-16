package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.SolutionDao;
import cn.xl.examination.entity.Solution;
import cn.xl.examination.service.SolutionService;
import org.springframework.stereotype.Service;

/**
 * (Solution)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Service("solutionService")
public class SolutionServiceImpl extends ServiceImpl<SolutionDao, Solution> implements SolutionService {

}

