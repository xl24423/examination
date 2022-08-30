package cn.xl.examination.service.impl;
import cn.xl.examination.dao.MajorDao;
import cn.xl.examination.entity.Major;
import cn.xl.examination.service.MajorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Major)表服务实现类
 *
 * @author makejava
 * @since 2022-08-30 11:18:37
 */
@Service("majorService")
public class MajorServiceImpl extends ServiceImpl<MajorDao, Major> implements MajorService {
    @Autowired
    MajorDao majorDao;

    @Override
    public List<Major> getAllMajor() {
        return majorDao.getAllMajor();
    }
}

