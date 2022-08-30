package cn.xl.examination.service;

import cn.xl.examination.vo.RegisterVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.data.relational.core.sql.In;

import java.io.IOException;
import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-07-13 11:20:54
 */
public interface UserService extends IService<User> {

    void register(RegisterVO registerVO) throws IOException;

    PageInfo<User> getAllUser(Integer pageNum, Integer pageSize);

    User getUserByUsername(String username);

    Integer deleteById(Integer id);

    void editUser(Integer id,String username, String password, String name, String tel, Integer roleId);

    PageInfo<User> searchAllUser(String username, String name, String tel,Integer pageNum,Integer pageSize);

    Integer enableUser(Integer id);

    PageInfo<User> AllCheckUser(Integer pageNum, Integer pageSize);

    PageInfo<User> searchAllCheckUser(String username, String name, String tel, Integer pageNum, Integer pageSize);

    Integer checkUser(Integer id);
}

