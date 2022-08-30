package cn.xl.examination.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.xl.examination.common.lang.Config;
import cn.xl.examination.common.lang.Result;
import cn.xl.examination.exception.ServiceException;
import cn.xl.examination.service.impl.UserDetailsServiceImpl;
import cn.xl.examination.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwt = request.getHeader(jwtUtils.getHeader());
        if (jwt == null) {
            jwt = request.getParameter("token");
        }
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }

        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        Result result = new Result();

        Claims claims = jwtUtils.getClaimByToken(jwt);
        if (claims == null) {
            result.setAuthError();
            result.setMsg("token已过期,请重新登录");
            response.getWriter().write(JSONUtil.toJsonStr(result));
            return;
        }

        if (jwtUtils.isTokenExpired(claims)) {
            result.setAuthError();
            result.setMsg("token已过期,请重新登录");
            response.getWriter().write(JSONUtil.toJsonStr(result));
            return;
        }
        String username = claims.getSubject();
        // 获取用户授权相关信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}
