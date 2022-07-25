package cn.xl.examination.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 配置spring的类都需要添加以下注解
// springMvc也是spring衍生的框架
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 实现跨域功能
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置当前项目所有的请求都允许跨域
        registry.addMapping("/**"). // 匹配访问任何资源路径
                allowedOrigins("*").                   // 允许什么样的源头路径可以跨域访问
                allowedMethods("*").                   // 允许任何方法访问(get/post.....)
                allowedHeaders("*").                   // 允许任何请求头
                allowCredentials(true);                // 允许携带cookie进行访问

    }
}
