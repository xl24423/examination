package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.ResourcesDao;
import cn.xl.examination.entity.Resources;
import cn.xl.examination.service.ResourcesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public PageInfo<Resources> getAllVideo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Resources> allResource = resourcesDao.findAllResource();
        return new PageInfo<>(allResource) ;
    }

    @Override
    public Integer deleteResource(Integer id) {
        return resourcesDao.deleteByResourceId(id);
    }
}

