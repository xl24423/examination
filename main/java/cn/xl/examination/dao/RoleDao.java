package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * (Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@Repository
public interface RoleDao extends BaseMapper<Role> {

}

