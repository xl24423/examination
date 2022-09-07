package cn.xl.examination.controller;



import cn.xl.examination.common.lang.Result;
import cn.xl.examination.entity.User;
import cn.xl.examination.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.Pass;
import cn.xl.examination.service.PassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Pass)表控制层
 *
 * @author makejava
 * @since 2022-09-07 10:45:59
 */
@RestController
@RequestMapping("pass")
@Slf4j
public class PassController extends ApiController {
    @Resource
    PassService passService;
    @Resource
    UserService userService;
    @PostMapping("")
    public Result post(@AuthenticationPrincipal UserDetails userDetails){
        log.debug("发放证书");
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        passService.post(user.getId());
        result.setSuccess(userDetails);
        return result;
    }
    @GetMapping("/del")
    public Result del(@AuthenticationPrincipal UserDetails userDetails){
        log.debug("删除证书");
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        passService.del(user.getId());
        result.setSuccess(userDetails);
        return result;
    }
    @GetMapping("/passCheck")
    public Result passCheck(@AuthenticationPrincipal UserDetails userDetails){
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        Pass pass = passService.getMyPass(user.getId());
        result.setSuccess(userDetails);
        result.setData(pass!=null);
        return result;
    }
}

