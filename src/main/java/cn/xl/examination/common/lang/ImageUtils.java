package cn.xl.examination.common.lang;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/*
 * 实现获取验证码图像功能的工具类
 * 返回一个BufferedImage对象
 *
 * */
public class ImageUtils {

    // 随机字符集合中去除掉了0和o，O，1和l，因为这些不易区分
    private static String CHECK_CODES = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";


    //  该方法主要作用是生成随机的颜色
    private static Color getRandColor(int s, int e) {
        Random random = new Random();
        if (s > 255)
            s = 255;
        if (e > 255)
            e = 255;
        int r, g, b;
        r = s + random.nextInt(e - s); // 随机生成RGB颜色中的r值
        g = s + random.nextInt(e - s); // 随机生成RGB颜色中的g值
        b = s + random.nextInt(e - s); // 随机生成RGB颜色中的b值
        return new Color(r, g, b);
    }


    /*
    @param:
     width , height 指定生成验证码的宽度和高度
     HttpServletRequest request 传入需要获取验证码图片的会话请求，将验证码四位验证码放入到session域中
    @return:
    BufferedImage image对象
    */
    public static BufferedImage getPicture(int width, int height, String id) {
        // 创建BufferedImage对象,其作用相当于一图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics(); // 创建Graphics对象,其作用相当于画笔
        Graphics2D g2d = (Graphics2D) g; // 创建Grapchics2D对象

        Random random = new Random();
        Font mfont = new Font("幼圆", Font.BOLD, 25); // 定义字体样式，显示大小
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height); // 绘制背景图片大小
        g.setFont(mfont); // 设置字体
        g.setColor(getRandColor(180, 200));

        // 绘制50条颜色和位置全部为随机产生的线条,该线条为2f
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int x1 = random.nextInt(6) + 1;
            int y1 = random.nextInt(12) + 1;
            BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL); // 定制线条样式
            Line2D line = new Line2D.Double(x, y, x + x1, y + y1);
            g2d.setStroke(bs);
            g2d.draw(line); // 绘制直线
        }

        //用来保存验证码字符串文本内容
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; ++i) {// 随机生成4个字符
            /*random.nextInt(CHECK_CODES.length())使用这个方法获取到一个从0到CHECK_CODES.length()
             *包含0，不包含    CHECK_CODES.length()的一个随机数
             *使用charAt根据下标的方式取出字符。
             */
            char charAt = CHECK_CODES.charAt(random.nextInt(CHECK_CODES.length()));
            String sTemp = String.valueOf(charAt);
            sb.append(sTemp);

            Color color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), random.nextInt(110));
            g.setColor(color);
            // 将生成的随机数进行随机缩放并旋转制定角度 PS.建议不要对文字进行缩放与旋转,因为这样图片可能不正常显示

            /* 将文字旋转制定角度 */
            Graphics2D g2d_word = (Graphics2D) g;
            AffineTransform trans = new AffineTransform();
            trans.rotate((45) * 3.14 / 180, 15 * i + 8, 7);

            /* 缩放文字 */
            float scaleSize = random.nextFloat() + 0.8f;
            if (scaleSize > 1f)
                scaleSize = 1f;
            trans.scale(scaleSize, scaleSize);
            g2d_word.setTransform(trans);
            g.drawString(sTemp, 15 * i + 18, 14);
        }
        /*
         * 将生成的四位随机验证码，放入session域中，方便登录controller的获取，验证。
         *当有controller调用获取验证码工具类时，需要传入本次请求的会话request，并获取session域。将验证码数据放入其中。
         * */

        Config.getJedis().setex("randCheckCode"+id,60,sb.toString());
        g.dispose(); // 释放g所占用的系统资源

        //返回画布
        return image;
    }

}