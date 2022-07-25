package cn.xl.examination.controller;

import cn.xl.examination.exception.ServiceException;
import cn.xl.examination.service.UserService;
import cn.xl.examination.vo.RegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class SystemController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public String register(@Validated RegisterVO registerVO, BindingResult result) throws IOException {
        log.debug("注册信息:"+registerVO);
            if (result.hasErrors()){
                return result.getFieldError().getDefaultMessage();
            }
            userService.register(registerVO);
            return "ok";
    }

}
