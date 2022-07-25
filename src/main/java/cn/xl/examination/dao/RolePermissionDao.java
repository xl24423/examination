package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.RolePermission;
import org.springframework.stereotype.Repository;

/**
 * (RolePermission)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Repository
public interface RolePermissionDao extends BaseMapper<RolePermission> {

}

