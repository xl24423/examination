package cn.xl.examination.dao;

import cn.xl.examination.entity.Permission;
import cn.xl.examination.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Select("select * from user where isCheck = 1")
    List<User> findAllUser();

    @Delete("delete from user where id = #{id}")
    Integer deleteOne(Integer id);

    @Update("update user set username=#{username},password=#{password},name=#{name},tel=#{tel},role_id=#{roleId} where id = #{id}")
    void edit(Integer id, String username, String password, String name, String tel, Integer roleId);

    @Select("select * from user where isCheck = 1 and username like CONCAT('%', #{username}, '%') or name like CONCAT('%', #{name}, '%') or tel like CONCAT('%', #{tel}, '%')  ")
    List<User> searchAllUser(String username, String name, String tel);

    @Select("select username from user where id = #{userId}")
    String backUserName(String userId);

    @Update("update user set enable = #{userEnable} where id = #{id}")
    Integer enableUser(Integer id, Integer userEnable);

    @Select("select enable from user where id = #{id}")
    Integer searchEnable(Integer id);

    @Select("select * from user where isCheck = 0")
    List<User> findAllCheckUser();

    @Select("select * from user where isCheck = 0 and username like CONCAT('%', #{username}, '%') or name like CONCAT('%', #{name}, '%') or tel like CONCAT('%', #{tel}, '%')  ")
    List<User> searchAllCheckUser(String username, String name, String tel);
    @Update("update user set isCheck = 1 where id = #{id}")
    Integer checkUser(Integer id);
}

