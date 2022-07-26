package cn.xl.examination.security;

import cn.hutool.core.util.StrUtil;
import cn.xl.examination.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Autowired
    JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)){
            chain.doFilter(request,response);
            return;
        }
        Claims claims = jwtUtils.getClaimByToken(jwt);
        if (claims == null){
            throw new JwtException("token 异常");
        }
        if (jwtUtils.isTokenExpired(claims)){
            throw new JwtException("token 已过期");
        }
        String username = claims.getSubject();
        // 获取用户授权相关信息
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username,null,null);
        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request,response);
    }
}
