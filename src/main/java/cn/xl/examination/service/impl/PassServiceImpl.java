package cn.xl.examination.service.impl;

import cn.xl.examination.exception.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.PassDao;
import cn.xl.examination.entity.Pass;
import cn.xl.examination.service.PassService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Pass)表服务实现类
 *
 * @author makejava
 * @since 2022-09-07 10:45:59
 */
@Service("passService")
public class PassServiceImpl extends ServiceImpl<PassDao, Pass> implements PassService {
    @Resource
    PassDao passDao;
    @Override
    public void post(Integer id) {
        Pass pass = passDao.getById(id);;
        if (pass != null){
            passDao.deleteById(id);
        }
        Integer post = passDao.post(id);
        if (post != 1) {
            throw new ServiceException("数据库异常,证书发放失败,请联系管理员");
        }
    }

    @Override
    public void del(Integer id) {
        Integer i = passDao.del(id);
        if (i!=1){
            throw new ServiceException("证书重置失败,请先里管理员");
        }
    }

    @Override
    public Pass getMyPass(Integer id) {
        return passDao.getById(id);
    }
}

