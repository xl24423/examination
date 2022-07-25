package cn.xl.examination.security;

import cn.xl.examination.service.impl.SpringDataUserDetailsService;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.ClassUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

// SpringBoot启动时扫描到@Configuration标记的类,会自动加载其中的配置
// 所有SpringBoot框架环境下@Configuration必须写才能配置生效
@Configuration
// 启动Spring-Security配置的注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // WebSecurityConfigurerAdapter是我们需要基础的父类
    // 这个父类提供了配置Spring-Security运行的基本方法
    // 我们要想修改配置的话,重写它的方法即可

    // 下面的配置是让我们编写的UserDetailsServiceImpl类生效的
    @Autowired
    private SpringDataUserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 让Spring-Security框架进行登录操作时
        // 调用我们编写的userDetailsService中的方法
        auth.userDetailsService(userDetailsService);
    }

    // 配置页面权限的方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()  // 设置禁用防跨域攻击
                .authorizeRequests()  // 开始设置页面访问权限
                .antMatchers(// 指定路径
                        "/register.html",
                        "/register",
                        "/login",
                        "http://localhost:8080/login.html",
                        "/js/**",
                        "/css/**",
                        "/bower_components/**",
                        "/img/**"
                ).permitAll()  // 上述路径全部放行(不登录就能访问)
                .anyRequest()  // 除此之外的其它请求
                .authenticated() // 需要登录才能访问
                .and()         // 这是个分割,上面配置已经完毕下面编写新配置
                .formLogin()  // 支持表单登录
                .loginPage("http://localhost:8080/login.html")  // 配置登录页为login.html
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login") // 配置表单提交登录信息的路径
                .failureUrl("/login.html?error") // 配置登录失败跳转的路径
                .defaultSuccessUrl("http://localhost:8080/") //登录成功后跳转的页面*
                .and() //登录设置完成,开始设置登出
                .logout()  // 开始设置登出
                .logoutUrl("http://localhost:9090/logout")  // 设置登出路径
                .logoutSuccessUrl("/login.html?logout");//设置登出成功后跳转的页面
        /*
            defaultSuccessUrl设置的是登录成功时默认跳转的页面
            特指用户没有指定要访问的页面时,登录成功时跳转的页面
            如果用户指定的要跳转的页面,登录成功时优先访问用户指定的页面
         */
    }
}

