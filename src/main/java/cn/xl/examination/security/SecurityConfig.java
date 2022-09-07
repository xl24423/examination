package cn.xl.examination.security;

import cn.xl.examination.controller.ExceptionControllerAdvice;
import cn.xl.examination.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// SpringBoot启动时扫描到@Configuration标记的类,会自动加载其中的配置
// 所有SpringBoot框架环境下@Configuration必须写才能配置生效
@Configuration
// 启动Spring-Security配置的注解
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] URL_WHITELIST = {
            "/login",       // 登陆接口
            "/logout",      // 登出接口
            "/user/register",            // 注册接口
            "/favicon.ico",
            "http://127.0.0.1:8080/login",      // 登陆页面
            "/static/**",           // 静态资源路径
            "/resources/image",     // 回显上传的图片
            "/major/allMajor",      // 获取所有专业
            "/checkCode"            // 获取验证码
    };

    // WebSecurityConfigurerAdapter是我们需要基础的父类
    // 这个父类提供了配置Spring-Security运行的基本方法
    // 我们要想修改配置的话,重写它的方法即可

    // 下面的配置是让我们编写的UserDetailsServiceImpl类生效的
    @Autowired(required = false)
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 让Spring-Security框架进行登录操作时
        // 调用我们编写的userDetailsService中的方法
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    VerifyCodeFilter verifyCodeFilter;

    // 配置页面权限的方法
    @ExceptionHandler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()  // 设置禁用防跨域攻击
                .formLogin().loginPage("http://127.0.0.1:8080/login").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)       // 禁用session
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()         // 配置白名单
                .anyRequest().authenticated()
//                 异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                //配置自定义过滤器
                .and()
                .addFilterBefore(verifyCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*  解决前端跨域的问题*/
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");//修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        configuration.addAllowedMethod("*");//修改为添加而不是设置
        configuration.addAllowedHeader("*");
        //这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}


