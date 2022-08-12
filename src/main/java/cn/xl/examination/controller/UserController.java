package cn.xl.examination.controller;

import cn.xl.examination.common.lang.Result;
import cn.xl.examination.entity.User;
import cn.xl.examination.service.UserService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2022-07-13 11:20:52
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    UserService userService;
    @Resource
    PasswordEncoder passwordEncoder;

    //    /**
//     * 分页查询所有数据
//     *
//     * @param page 分页对象
//     * @param user 查询实体
//     * @return 所有数据
//     */
//    @GetMapping
//    public R selectAll(Page<User> page, User user) {
//        return success(this.userService.page(page, new QueryWrapper<>(user)));
//    }
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('/page')")
    public PageInfo<User> AllUser(Integer pageNum, Integer pageSize) {
        log.debug("," + pageNum + "," + pageSize);
        return userService.getAllUser(pageNum, pageSize);
    }

    @DeleteMapping("/del")
    public Result delete(Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        Result result = new Result();
        if (!userService.getUserByUsername(userDetails.getUsername()).getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        if (id == null) {
            result.setPathError();
            return result;
        }
        Integer i = userService.deleteById(id);
        if (i != 1) {
            result.setDataBaseError();
            return result;
        }
        result.setSuccess(userDetails);
        result.setMsg("删除用户成功");
        return result;
    }

    @PostMapping("/edit")
    public Result edit(Integer id,
                       String username,
                       String password,
                       String name,
                       String tel,
                       Integer roleId,
                       @AuthenticationPrincipal UserDetails userDetails) {
        Result result = new Result();
        User NowUser = userService.getUserByUsername(userDetails.getUsername());
        if (!username.equals(NowUser.getUsername()) && !NowUser.getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        if (roleId == null ||
                !username.trim().matches("^.{2,10}$") ||
                !tel.trim().matches("^1[3456789][0-9]{9}$") ||
                !password.trim().matches("^[0-9a-zA-z._]{6,12}$") ||
                !name.trim().matches("^.{1,5}$")
        ) {
            result.setPathError();
            return result;
        }
        password = passwordEncoder.encode(password);
        log.debug("这里是用户信息:" + id + "," + username + "," + password + "," + name + "," + tel + "," + roleId);
        userService.editUser(id, username, password, name, tel, roleId);
        result.setSuccess(userDetails);
        result.setMsg("更新成功");
        return result;
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('/page')")
    public PageInfo<User> search(String username, String name, String tel, Integer pageNum, Integer pageSize) {
        if (username != null && username.equals("")) {
            username = null;
        }
        if (name != null && name.equals("")) {
            name = null;
        }
        if (tel != null && tel.equals("")) {
            tel = null;
        }
        return userService.searchAllUser(username, name, tel, pageNum, pageSize);
    }

}

