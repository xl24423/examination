package cn.xl.examination.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // 支持重定向
@Slf4j
@CrossOrigin
public class HomeController {
    public static final GrantedAuthority ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    @GetMapping("/index.html")
    public String index(@AuthenticationPrincipal UserDetails userDetails) {
        log.debug("进入到了重定向");
        if (userDetails.getAuthorities().contains(ADMIN)) {
            return "redirect:http://localhost:8080";
        }
        return "redirect:http://localhost:8080";
    }
    @GetMapping("/indexError")
    public String error(){
        return "redirect:http://localhost:8080?error";
    }

}
