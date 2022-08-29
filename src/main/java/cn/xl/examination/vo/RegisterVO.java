package cn.xl.examination.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
@Data
public class RegisterVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^\\w{3,10}$",message = "用户名必须由3到10个数字字母下划线组成")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^\\w{6,20}$",message = "密码必须由6到20个数字字母下划线组成")  // \\w 表示字符数字下划线
    private String password; //密码
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9][0-9]{9}$",message = "请输入正确的手机号")
    private String phone; //电话
    @NotBlank(message = "邀请码不能为空")
    private String invite;  //邀请码
    @NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "^.{1,11}$",message = "姓名最大不超过11")
    private String name; //昵称
    @NotBlank(message = "请确认密码")
    private String confirm; //确认密码
    @NotNull(message = "必须选择图片")
    private MultipartFile file;
    @NotBlank(message = "类型不可为空")
    private String major;
    @NotBlank(message = "姓名不为空")
    private String gender;  // 性别
}
