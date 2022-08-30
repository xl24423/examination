package cn.xl.examination.security;

import cn.hutool.json.JSONUtil;
import cn.xl.examination.common.lang.Config;
import cn.xl.examination.common.lang.Result;
import cn.xl.examination.exception.ServiceException;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.json.Json;
import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.rmi.ServerException;

@Component
public class VerifyCodeFilter extends GenericFilterBean {
    private String defaultFilterProcessUrl = "/login" ;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws RuntimeException, ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if ("POST".equalsIgnoreCase( request.getMethod( )) && defaultFilterProcessUrl.equals(request.getServletPath())){
            String requestCaptcha = request.getParameter( "randCode" ) ;
            String id = request.getParameter("id");
            System.out.println("验证码id"+id);
            String genCaptcha = Config.getJedis().get("randCheckCode"+id);
            System.out.println("验证码验证码"+genCaptcha);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type", "text/html;charset=UTF-8");
            Result result = new Result();
            if (genCaptcha == null){
                result.setAuthError();
                result.setMsg("验证码失效");
                response.getWriter().write(JSONUtil.toJsonStr(result));
                return;
            }
            if (StringUtils.isEmpty(requestCaptcha)){
                result.setAuthError();
                result.setMsg("验证码不能为空!");
                response.getWriter().write(JSONUtil.toJsonStr(result));
                return;
            }
            if ( !genCaptcha.equalsIgnoreCase(requestCaptcha)){
                result.setMsg("验证码出错");
                response.getWriter().write(JSONUtil.toJsonStr(result));
                return;
            }
        }
        filterChain.doFilter( request, response );
    }

}
