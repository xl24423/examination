package cn.xl.examination.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // 支持重定向
public class HomeController {
    public static final GrantedAuthority ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    @GetMapping("/index.html")
    public String index(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getAuthorities().contains(ADMIN)) {
            return "redirect:http://localhost:8080";
        }
        return "redirect:/index_user.html";
    }

}
