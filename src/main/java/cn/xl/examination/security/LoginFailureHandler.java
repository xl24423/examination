package cn.xl.examination.security;

import cn.hutool.json.JSONUtil;
import cn.xl.examination.common.lang.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            Result result;
            if (e.getMessage().equals("Bad credentials")){
                result = Result.fail("用户名或密码错误");
            }else{
                result = Result.fail(e.getMessage());
            }
            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));

            outputStream.flush();
            outputStream.close();
    }
}
