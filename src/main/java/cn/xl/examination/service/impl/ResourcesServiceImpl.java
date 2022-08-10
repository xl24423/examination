package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.ResourcesDao;
import cn.xl.examination.entity.Resources;
import cn.xl.examination.service.ResourcesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Resources)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Service("resourcesService")
public class ResourcesServiceImpl extends ServiceImpl<ResourcesDao, Resources> implements ResourcesService {
    @Resource
    ResourcesDao resourcesDao;
    @Override
    public Integer upFile(Resources resources) {
         int i = resourcesDao.insertOne(resources.getAddress(),resources.getName(),resources.getContent());
         return i;
    }
}

