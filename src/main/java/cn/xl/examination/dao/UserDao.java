package cn.xl.examination.dao;

import cn.xl.examination.entity.Permission;
import cn.xl.examination.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 11:20:53
 */
@Repository
public interface UserDao extends BaseMapper<User> {
    @Select("select * from user where username = #{username}")
    User selectByUserName(String username);
    @Select("select p.id,p.name,p.desc from permission p " +
            "left join role_permission rp on p.id = rp.permission_id " +
            "left join role r on rp.role_id = r.id " +
            "left join user u on r.id = u.role_id " +
            "where u.id = #{id}")
    List<Permission> selectPermissionByUserId(Integer id);
    @Select("select r.id,r.name from role r left join user u on r.id = u.role_id where u.id = #{id}")
    List<Role> getUserRoleById(Integer id);
    @Select("select * from user where tel = #{tel}")
    User selectByPhone(String tel);
    @Select("select * from user")
    List<User> findAllUser();
}

