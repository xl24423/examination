package cn.xl.examination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.Resources;
import com.github.pagehelper.PageInfo;
import org.springframework.data.relational.core.sql.In;

/**
 * (Resources)表服务接口
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
public interface ResourcesService extends IService<Resources> {
     Integer upFile(Resources resources);

    PageInfo<Resources> getAllVideo(Integer pageNum, Integer pageSize);

    Integer deleteResource(Integer id);
}

