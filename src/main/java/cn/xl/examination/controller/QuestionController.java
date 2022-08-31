package cn.xl.examination.controller;

import cn.xl.examination.common.lang.Result;
import cn.xl.examination.entity.Question;
import cn.xl.examination.entity.QuestionImage;
import cn.xl.examination.entity.Resources;
import cn.xl.examination.entity.User;
import cn.xl.examination.service.QuestionService;
import cn.xl.examination.service.UserService;
import cn.xl.examination.vo.TableDataVO;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * (Question)表控制层
 *
 * @author makejava
 * @since 2022-08-12 16:25:31
 */
@RestController
@RequestMapping("/question")
@Slf4j

public class QuestionController extends ApiController {
    @Resource
    UserService userService;
    @Resource
    QuestionService questionService;

    @PostMapping("/questionAdd")
    @PreAuthorize("hasAuthority('/question/*')")
    public Result questionAdd(HttpServletRequest request,
            @AuthenticationPrincipal UserDetails userDetails,
                              String analysis,
                              String score,
                              String type,
                              String bankId,
                              String questionContent) {
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        if (!user.getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        String tableData = request.getParameter("tableData");
        JSONArray tableDataVOS = JSONArray.fromObject(tableData);
        String s = tableDataVOS.toString();
        Integer n;
        if (!s.equals("[]")){
            s = s.substring(2);
            s = s.substring(0,s.length()-2);
            String[] arr = s.split("},\\{");
            TableDataVO[] myTable = new TableDataVO[arr.length];
            for (int i=0;i<arr.length;i++){
                myTable[i] = new TableDataVO();
                String[] param = arr[i].split(",");
                String param0 = param[0].split(":")[1];
                if ( param0.equals("true")){
                    myTable[i].setType(true);
                }
                String param1 = param[1].split(":")[1];
                myTable[i].setContent(param1.substring(1,param1.length()-1));
                String param2 = param[2].split(":")[1];
                myTable[i].setOption(param2.substring(1,param2.length()-1));
            }
            n = questionService.addQuestion(questionContent,myTable,bankId,analysis,score,type, String.valueOf(user.getId()));
        }else{
            TableDataVO[] myTable = new TableDataVO[1];
            String solution = request.getParameter("solution");
            myTable[0] = new TableDataVO();
            myTable[0].setContent(solution);
            n = questionService.addQuestion(questionContent,myTable,bankId,analysis,score,type, String.valueOf(user.getId()));
        }

        result.setSuccess(userDetails);
        result.setData(n);
        return result;
    }
    @Value("${image-path}")
    String resourcePath;
    @PostMapping("/resource")
    @PreAuthorize("hasAuthority('/question/*')")
    public Result postResource(@AuthenticationPrincipal UserDetails userDetails, Integer id, MultipartFile file) throws IOException {
        Result result = new Result();
        if (!userService.getUserByUsername(userDetails.getUsername()).getRoleId().equals("1")){
            result.setAuthError();
            return result;
        }
        if (file == null){
            result.setPathError();
            result.setData("文件出错为空");
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
        String url = "http://localhost:9090/static/image/"+path+"/"+name;
        log.debug("回显图片的路径为:{}",url);
        QuestionImage questionImage = new QuestionImage().setQuestionId(id).setImageUrl(url);
        Integer i = questionService.postResource(questionImage);
        if (i!=1){
            result.setDataBaseError();
            return result;
        }
        result.setSuccess(userDetails);
        result.setMsg("图片保存成功");
        return result;
    }
    @GetMapping("/questions")
    public Result getAllQuestions(@AuthenticationPrincipal UserDetails userDetails,Integer pageNum, Integer pageSize){
        Result result = new Result();
        if (!userService.getUserByUsername(userDetails.getUsername()).getRoleId().equals("1")){
            result.setAuthError();
            return result;
        }
        PageInfo<Question> questionPageInfo = questionService.getAllQuestions(pageNum,pageSize);

        result.setSuccess(userDetails);
        result.setData(questionPageInfo);
        return result;
    }

    @GetMapping("/exam")
    public Result backExamQuestions(@AuthenticationPrincipal UserDetails userDetails, Integer id){
        Result result = new Result();
        List<Question> questions = questionService.backExamQuestions(id);
        result.setSuccess(userDetails);
        result.setData(questions);
        return result;
    }
}

