package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.RolePermissionDao;
import cn.xl.examination.entity.RolePermission;
import cn.xl.examination.service.RolePermissionService;
import org.springframework.stereotype.Service;

/**
 * (RolePermission)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {

}

