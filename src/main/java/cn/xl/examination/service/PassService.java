package cn.xl.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.Pass;

/**
 * (Pass)表服务接口
 *
 * @author makejava
 * @since 2022-09-07 10:45:59
 */
public interface PassService extends IService<Pass> {

    void post(Integer id);

    void del(Integer id);

    Pass getMyPass(Integer id);
}

