package cn.xl.examination.controller;

import cn.xl.examination.common.lang.ImageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CheckController {

    /*
     * 当页面请求时，使用画布获取一个验证码图像
     * 返回到页面

     * 接收前端获取验证码的请求，使用画布随机生成一个四位数的验证码图像
     * 将生成的四位数字，数字或字母放在一个session域中使得登录验证的controller中可以获取到该值，判断登陆验证
     * 使用mageIO.write(image, "JPEG", response.getOutputStream());输出图片
     * 使其再前端可以接收再图片显示出来
     * */
    @GetMapping("/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codeId = request.getParameter("codeId");
        System.out.println(codeId);
        //设置编码格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 设置不缓存图片
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);

        // 指定生成的响应图片,一定不能缺少这句话,否则错误.
        response.setContentType("image/jpeg");
        /*
         * @param
         * 传入生成图像长度和宽度，还有本次会话的请求HttpServletRequest对象request
         * @return
         * 返回一个BufferedImage对象
         * */
        BufferedImage image = ImageUtils.getPicture(80, 35, codeId);
        // 将图像输出到response输出流中。
        //response.getOutputStream()获取response输出流
        ImageIO.write(image, "JPEG", response.getOutputStream()); // 输出图片
    }
}
