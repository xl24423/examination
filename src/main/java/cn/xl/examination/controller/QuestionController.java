package cn.xl.examination.controller;

import cn.xl.examination.common.lang.Result;
import cn.xl.examination.dao.AnswerDao;
import cn.xl.examination.dao.QuestionImageDao;
import cn.xl.examination.entity.*;
import cn.xl.examination.service.QuestionService;
import cn.xl.examination.service.UserService;
import cn.xl.examination.vo.OptionVO;
import cn.xl.examination.vo.QuestionVO;
import cn.xl.examination.vo.TableDataVO;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.concurrent.ConcurrentHashMap;

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
        return convey(request,userDetails,analysis,score,type,bankId,questionContent);
    }
    private Result convey(HttpServletRequest request,
                          @AuthenticationPrincipal UserDetails userDetails,
                          String analysis,
                          String score,
                          String type,
                          String bankId,
                          String questionContent){
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
        if (!s.equals("[]")) {
            s = s.substring(2);
            s = s.substring(0, s.length() - 2);
            String[] arr = s.split("},\\{");
            TableDataVO[] myTable = new TableDataVO[arr.length];
            for (int i = 0; i < arr.length; i++) {
                myTable[i] = new TableDataVO();
                String[] param = arr[i].split(",");
                String param0 = param[0].split(":")[1];
                if (param0.equals("true")) {
                    myTable[i].setType(true);
                }
                String param1 = param[1].split(":")[1];
                myTable[i].setContent(param1.substring(1, param1.length() - 1));
                String param2 = param[2].split(":")[1];
                myTable[i].setOption(param2.substring(1, param2.length() - 1));
            }
            n = questionService.addQuestion(questionContent, myTable, bankId, analysis, score, type, String.valueOf(user.getId()));
        } else {
            TableDataVO[] myTable = new TableDataVO[1];
            String solution = request.getParameter("solution");
            myTable[0] = new TableDataVO();
            myTable[0].setContent(solution);
            n = questionService.addQuestion(questionContent, myTable, bankId, analysis, score, type, String.valueOf(user.getId()));
        }

        result.setSuccess(userDetails);
        result.setData(n);
        return result;
    }

    @PostMapping("/questionEdit")
    @PreAuthorize("hasAuthority('/question/*')")
    @Transactional
    public Result questionEdit(HttpServletRequest request,
                              @AuthenticationPrincipal UserDetails userDetails,
                              String id,
                              String analysis,
                              String score,
                              String type,
                              String bankId,
                              String questionContent) {
        questionService.deleteByQuestionId(Integer.parseInt(id));
        return convey(request,userDetails,analysis,score,type,bankId,questionContent);
    }

    @Value("${image-path}")
    String resourcePath;

    @PostMapping("/resource")
    @PreAuthorize("hasAuthority('/question/*')")
    public Result postResource(@AuthenticationPrincipal UserDetails userDetails, Integer id, MultipartFile file) throws IOException {
        Result result = new Result();
        if (!userService.getUserByUsername(userDetails.getUsername()).getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        if (file == null) {
            result.setPathError();
            result.setData("文件出错为空");
            return result;
        }
        // 我们要确定文件保存的位置
        // 文件保存的路径是 D:/upload/年/月/日
        // 先获得当前日期的字符串做路径
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
        // 确定要上传的文件夹
        File folder = new File(resourcePath, path);
        // 创建这个文件夹
        folder.mkdirs();
        // 下面要确定上传的文件名
        // 获得文件原名,以便获取后缀名
        String fileName = file.getOriginalFilename();
        fileName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(fileName);
        String name = UUID.randomUUID().toString() + fileName;
        File newFile = new File(folder, name);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                newFile.delete();
//            }
//        }, 10 * 1000);       // 设置定时删除
        log.debug("文件上传到:{}", newFile.getAbsolutePath());
        file.transferTo(newFile);
        // 为了显示回显,我们需要返回可以访问上传的图片的路径
        // 我们上传的图片要想访问,需要访问静态资源服务器的路径,可能的格式如下
        // http://localhost:8899/2022/03/23/xxx-xxx-xxx.jpg
        String url = "http://127.0.0.1:9090/static/image/" + path + "/" + name;
        log.debug("回显图片的路径为:{}", url);
        QuestionImage questionImage = new QuestionImage().setQuestionId(id).setImageUrl(url);
        Integer i = questionService.postResource(questionImage);
        if (i != 1) {
            result.setDataBaseError();
            return result;
        }
        result.setSuccess(userDetails);
        result.setMsg("图片保存成功");
        return result;
    }

    @GetMapping("/questions")
    @PreAuthorize("hasAuthority('/question/*')")
    public Result getAllQuestions(@AuthenticationPrincipal UserDetails userDetails, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        if (!userService.getUserByUsername(userDetails.getUsername()).getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        PageInfo<Question> questionPageInfo = questionService.getAllQuestions(pageNum, pageSize);
        result.setSuccess(userDetails);
        result.setData(questionPageInfo);
        return result;
    }

    @Resource
    QuestionImageDao questionImageDao;
    @Resource
    AnswerDao answerDao;
    @GetMapping("/exam")
    public Result backExamQuestions(@AuthenticationPrincipal UserDetails userDetails, Integer id) {
        Result result = new Result();
        Question[] questions = questionService.backExamQuestions(id);
        List<QuestionVO> questionVOS = new ArrayList<>(questions.length);
        String url;
        for (Question q : questions) {

            if (q.getType().equals("1") || q.getType().equals("2")) {
                List<OptionVO> list = new ArrayList<>();
                if (q.getA().length() != 0) {
                    list.add(new OptionVO(q.getA(), "A"));
                }
                if (q.getB().length() != 0) {
                    list.add(new OptionVO(q.getB(), "B"));
                }
                if (q.getC().length() != 0) {
                    list.add(new OptionVO(q.getC(), "C"));
                }
                if (q.getD().length() != 0) {
                    list.add(new OptionVO(q.getD(), "D"));
                }
                OptionVO[] optionVOS = new OptionVO[list.size()];
                list.toArray(optionVOS);
                url = questionImageDao.selectUrl(q.getId());
                questionVOS.add(new QuestionVO(q.getId(),q.getType(), q.getQuestion(), "", optionVOS, url));
            } else {
                OptionVO[] optionVOS = new OptionVO[2];
                optionVOS[0] = new OptionVO("对", "true");
                optionVOS[1] = new OptionVO("错", "false");
                url = questionImageDao.selectUrl(q.getId());
                questionVOS.add(new QuestionVO(q.getId(),q.getType(), q.getQuestion(), "", optionVOS, url));
            }
        }
        result.setSuccess(userDetails);
        result.setData(questionVOS);
        return result;
    }
    @GetMapping("/examRecode")
    public Result backExamRecodeQuestions(@AuthenticationPrincipal UserDetails userDetails, Integer id) {
        Result result = new Result();
        Question[] questions = questionService.backQuestionsDetails(id);
        List<QuestionVO> questionVOS = new ArrayList<>(questions.length);
        String url;
        List<Answer> answers = answerDao.selectByIdAndUserId(id,userService.getUserByUsername(userDetails.getUsername()).getId());
        Map<Integer,String> map = new ConcurrentHashMap<>();
        for (Answer answer : answers) {
            map.put(Integer.valueOf(answer.getQuestionId()),answer.getUserAnswer());
        }
        for (Question q : questions) {
            if (q.getType().equals("1") || q.getType().equals("2")) {
                List<OptionVO> list = new ArrayList<>();
                if (q.getA().length() != 0) {
                    list.add(new OptionVO(q.getA(), "A"));
                }
                if (q.getB().length() != 0) {
                    list.add(new OptionVO(q.getB(), "B"));
                }
                if (q.getC().length() != 0) {
                    list.add(new OptionVO(q.getC(), "C"));
                }
                if (q.getD().length() != 0) {
                    list.add(new OptionVO(q.getD(), "D"));
                }
                OptionVO[] optionVOS = new OptionVO[list.size()];
                list.toArray(optionVOS);
                String ans = map.get(q.getId()); // 获取 map 中用户的回答
                    List<String> splits = new ArrayList<>(Arrays.asList(ans.split(",")));
                    if (splits.contains("A")){
                        optionVOS[0].setCurrent(true);
                    } if (splits.contains("B")){
                        optionVOS[1].setCurrent(true);
                    } if (splits.contains("C")){
                        optionVOS[2].setCurrent(true);
                    } if (splits.contains("D")){
                        optionVOS[3].setCurrent(true);
                    }
                url = questionImageDao.selectUrl(q.getId());
                questionVOS.add(new QuestionVO(q.getId(),q.getType(), q.getQuestion(), map.get(q.getId()), optionVOS, url,q.getScore(),q.getQuestionAnalysis(),q.getSolution()));
            } else {
                OptionVO[] optionVOS = new OptionVO[2];
                optionVOS[0] = new OptionVO("对", "true");
                optionVOS[1] = new OptionVO("错", "false");
                String ans = map.get(q.getId());
                if (ans.equals("true")){
                    optionVOS[0].setCurrent(true);
                }else if (ans.equals("false")){
                    optionVOS[1].setCurrent(true);
                }
                url = questionImageDao.selectUrl(q.getId());
                questionVOS.add(new QuestionVO(q.getId(),q.getType(), q.getQuestion(), map.get(q.getId()), optionVOS, url,q.getScore(),q.getQuestionAnalysis(),q.getSolution()));
            }
        }
        result.setSuccess(userDetails);
        result.setData(questionVOS);
        return result;
    }
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('/question/*')")
    public Result search(@AuthenticationPrincipal UserDetails userDetails, String questionName,String region,String questionContext, Integer pageNum, Integer pageSize){
        Result result = new Result();
        if (!userService.getUserByUsername(userDetails.getUsername()).getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        PageInfo<Question> questionPageInfo = questionService.searchAllQuestions(questionName,region,questionContext,pageNum,pageSize);
        result.setSuccess(userDetails);
        result.setData(questionPageInfo);
        return result;
    }
    @GetMapping("/searchOne")
    @PreAuthorize("hasAuthority('/question/*')")
    public Result searchOne(@AuthenticationPrincipal UserDetails userDetails, Integer id){
        Result result = new Result();
        Question question = questionService.searchOne(id);
        result.setCode(200);
        result.setData(question);
        return result;
    }
}

