package cn.xl.examination.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "http://localhost:[*]",       // 允许所有本地端口
                        "http://*.natappfree.cc",     // 允许所有NatApp子域名
                        "https://*.natappfree.cc"     // 支持HTTPS
                )
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization") // 暴露自定义头
                .allowCredentials(true)
                .maxAge(3600);
    }
}