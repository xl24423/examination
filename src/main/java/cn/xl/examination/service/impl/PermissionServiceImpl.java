package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.PermissionDao;
import cn.xl.examination.entity.Permission;
import cn.xl.examination.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * (Permission)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

}

