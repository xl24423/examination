package cn.xl.examination.service;

import cn.xl.examination.vo.RegisterVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xl.examination.entity.User;
import com.github.pagehelper.PageInfo;

import java.io.IOException;

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
}

