package cn.xl.examination.controller;


import cn.xl.examination.common.lang.Result;
import cn.xl.examination.entity.User;
import cn.xl.examination.exception.ServiceException;
import cn.xl.examination.service.QuestionService;
import cn.xl.examination.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.QuestionBank;
import cn.xl.examination.service.QuestionBankService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (QuestionBank)表控制层
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
@RestController
@RequestMapping("/questionBank")
@Slf4j
public class QuestionBankController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private QuestionBankService questionBankService;
    @Resource
    private UserService userService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('/questionBank/*')")
    public Result getAllQuestionBank(@AuthenticationPrincipal UserDetails userDetails, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        if (!user.getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        result.setSuccess(userDetails);
        result.setData(questionBankService.getAllQuestionBank(pageNum, pageSize));
        return result;
    }
    @Resource
    QuestionService questionService;
    @DeleteMapping
    @Transactional
    @PreAuthorize("hasAuthority('/questionBank/*')")
    public Result delete(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("id") Integer id) {
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        if (!user.getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        if (id == null) {
            result.setPathError();
            return result;
        }
        Integer integer = questionBankService.deleteOne(id);
        if (integer != 1) {
            result.setDataBaseError();
            return result;
        }
        questionService.deleteByQuestionBankId(id);
        result.setSuccess(userDetails);
        result.setMsg("删除成功");
        return result;
    }

    @GetMapping("/getAllQuestionBank")
    @PreAuthorize("hasAuthority('/questionBank/*')")
    public Result getAllQuestionBank(@AuthenticationPrincipal UserDetails userDetails) {
        Result result = new Result();
        if (!userService.getUserByUsername(userDetails.getUsername()).getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        List<QuestionBank> list = questionBankService.getAllQuestionBankDontPage();
        result.setSuccess(userDetails);
        result.setData(list);
        return result;
    }

    // 开启关闭考试功能
    @GetMapping("/act")
    @PreAuthorize("hasAuthority('/questionBank/*')")
    public void actExamination(@AuthenticationPrincipal UserDetails userDetails, String action, Integer id) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        if (!user.getRoleId().equals("1")) {
            return ;
        }
        questionBankService.actExamination(action, id);
    }
    // 学生开始考试
    @GetMapping("/actionExam")
    @PreAuthorize("hasAuthority('/questionBank/action')")
    public Result actionExam(@AuthenticationPrincipal UserDetails userDetails, Integer pageNum, Integer pageSize){
        Result result = new Result();
        PageInfo<QuestionBank> questionBankPageInfo = questionBankService.actionExam(pageNum,pageSize);
        result.setSuccess(userDetails);
        result.setData(questionBankPageInfo);
        return result;
    }
    // 学生开始考试搜索
    @GetMapping("/actionExamSearch")
    @PreAuthorize("hasAuthority('/questionBank/action')")
    public Result actionExamSearch(@AuthenticationPrincipal UserDetails userDetails,String name){
        Result result = new Result();
        QuestionBank questionBank = questionBankService.actionExamSearch(name);
        result.setSuccess(userDetails);
        result.setData(questionBank);
        return result;
    }
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('/questionBank/*')")
    public Result addQuestionBank(@AuthenticationPrincipal UserDetails userDetails, String title, String time)
    {
        Result result = new Result();
        if (!userService.getUserByUsername(userDetails.getUsername()).getRoleId().equals("1")) {
            result.setAuthError();
            return result;
        }
        QuestionBank questionBank = questionBankService.selectQuestionBankByName(title);
        if (questionBank != null){
            result.setCode(500);
            result.setMsg("已存在该名称的题库");
            return result;
        }
        questionBankService.addQuestionBank(userDetails.getUsername(),title,time);
        result.setSuccess(userDetails);
        return result;
    }
    @GetMapping("/selectOne")
    @PreAuthorize("hasAuthority('/questionBank/action')")
    public Result selectOne(@AuthenticationPrincipal UserDetails userDetails, Integer id){
        Result result = new Result();
        QuestionBank questionBank = questionBankService.selectOne(id);
        result.setSuccess(userDetails);
        result.setData(questionBank);
        return result;
    }
    @GetMapping("/time")
    public Result getTime(Integer id){
        Result result = new Result();
        String t = questionBankService.getTime(id);
        if (t==null || t.equals("") || t.length()<1){
            result.setDataBaseError();
            return result;
        }
        Integer time = Integer.parseInt(t);
        result.setCode(200);
        result.setData(time);
        return result;
    }
    @GetMapping("/selectByName")
    @PreAuthorize("hasAuthority('/questionBank/*')")
    public Result selectByName(String name){
        Result result = new Result();
        QuestionBank questionBank = questionBankService.likeName(name);
        result.setCode(200);
        result.setData(questionBank);
        return result;
    }

}

