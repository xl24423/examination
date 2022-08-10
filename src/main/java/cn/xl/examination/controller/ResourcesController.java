package cn.xl.examination.controller;



import cn.xl.examination.common.lang.Result;
import cn.xl.examination.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.Resources;
import cn.xl.examination.service.ResourcesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * (Resources)表控制层
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@RestController
@RequestMapping("resources")
@Slf4j
public class ResourcesController extends ApiController {
    @Value("${video-path}")
    public String resourcePath;

    @Resource
    ResourcesService resourcesService;
    @Resource
    UserService userService;

    @PostMapping("/upFile")
    public Result upFile(@AuthenticationPrincipal UserDetails userDetails, MultipartFile file, String videoName, String content) throws IOException {
        Result result = new Result();
        if (userService.getUserByUsername(userDetails.getUsername()).getRoleId() != 1){
            result.setCode(401);
            result.setMsg("你没有此权限");
            return result;
        }
        if (file == null || videoName.equals("") || content.equals("")){
            result.setCode(403);
            result.setMsg("视频或名称或描述不能为空");
            return result;
        }
// 我们要确定文件保存的位置
        // 文件保存的路径是 D:/upload/年/月/日
        // 先获得当前日期的字符串做路径
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
        // 确定要上传的文件夹
        File folder = new File(resourcePath,path);
        // 创建这个文件夹
        folder.mkdirs();
        // 下面要确定上传的文件名
        // 获得文件原名,以便获取后缀名
        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(fileName);
        String name = UUID.randomUUID().toString()+fileName;
        File newFile = new File(folder,name);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                newFile.delete();
//            }
//        }, 10 * 1000);       // 设置定时删除
        log.debug("文件上传到:{}",newFile.getAbsolutePath());
        file.transferTo(newFile);
        // 为了显示回显,我们需要返回可以访问上传的图片的路径
        // 我们上传的图片要想访问,需要访问静态资源服务器的路径,可能的格式如下
        // http://localhost:8899/2022/03/23/xxx-xxx-xxx.jpg
        String url = "http://localhost:9090/"+path+"/"+name;
        log.debug("回显图片的路径为:{}",url);
        Resources resources = new Resources();
        resources.setName(videoName).setContent(content).setAddress(url);
        log.debug("entity"+resources);
        Integer integer = resourcesService.upFile(resources);
        if (integer != 1){
            result.setCode(500);
            result.setMsg("数据库异常,请联系管理员");
            return result;
        }
        result.setCode(200);
        result.setMsg("上传成功");
        result.setData(resources);
        return result;
    }
}

