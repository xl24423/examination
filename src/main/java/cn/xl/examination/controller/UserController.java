package cn.xl.examination.controller;

import cn.xl.examination.entity.User;
import cn.xl.examination.service.UserService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private UserService userService;

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
    public PageInfo<User> AllUser(@AuthenticationPrincipal UserDetails userDetails, Integer pageNum, Integer pageSize){
          log.debug("开始查询:"+userDetails+","+pageNum+","+pageSize);
          return userService.getAllUser(userDetails.getUsername(),pageNum,pageSize);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody User user) {
        return success(this.userService.save(user));
    }

    /**
     * 修改数据
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody User user) {
        return success(this.userService.updateById(user));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userService.removeByIds(idList));
    }
    @GetMapping("/me")
    public String me(){
        return "me";
    }
}

