package cn.xl.examination.service;

import cn.xl.examination.entity.Major;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * (Major)表服务接口
 *
 * @author makejava
 * @since 2022-08-30 11:18:35
 */
public interface MajorService extends IService<Major> {

    List<Major> getAllMajor();
}

