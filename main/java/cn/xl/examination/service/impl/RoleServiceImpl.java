package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.RoleDao;
import cn.xl.examination.entity.Role;
import cn.xl.examination.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

}

