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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.util.ClassUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

// SpringBoot启动时扫描到@Configuration标记的类,会自动加载其中的配置
// 所有SpringBoot框架环境下@Configuration必须写才能配置生效
@Configuration
// 启动Spring-Security配置的注解
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] URL_WHITELIST={
      "/login",
      "/logout",
      "/favicon.ico"
    };

    // WebSecurityConfigurerAdapter是我们需要基础的父类
    // 这个父类提供了配置Spring-Security运行的基本方法
    // 我们要想修改配置的话,重写它的方法即可

    // 下面的配置是让我们编写的UserDetailsServiceImpl类生效的
    @Autowired(required = false)
    private SpringDataUserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 让Spring-Security框架进行登录操作时
        // 调用我们编写的userDetailsService中的方法
        auth.userDetailsService(userDetailsService);
    }

    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    // 配置页面权限的方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()  // 设置禁用防跨域攻击
                .formLogin().successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)       // 禁用session
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()         // 配置白名单
                .anyRequest().authenticated();

    }
}


