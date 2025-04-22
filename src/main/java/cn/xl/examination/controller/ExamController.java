package cn.xl.examination.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xl.examination.common.lang.Result;
import cn.xl.examination.entity.Answer;
import cn.xl.examination.entity.Question;
import cn.xl.examination.entity.User;
import cn.xl.examination.exception.ServiceException;
import cn.xl.examination.service.AnswerService;
import cn.xl.examination.service.QuestionService;
import cn.xl.examination.service.UserService;
import cn.xl.examination.vo.AnswerVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.Exam;
import cn.xl.examination.service.ExamService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * (Exam)表控制层
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@RestController
@RequestMapping("/exam")
@Slf4j
public class ExamController extends ApiController {
    @Resource
    AnswerService answerService;
    @Resource
    ExamService examService;
    @Resource
    UserService userService;
    @Resource
    QuestionService questionService;

    @PostMapping("")
    @Transactional
    public Result post(@AuthenticationPrincipal UserDetails userDetails, @RequestBody String answerVO) {
        Result result = new Result();
        JSONObject jsonObject = JSONUtil.parseObj(answerVO);
        AnswerVO[] answerList = jsonObject.get("answerList", AnswerVO[].class);
        Long starTime = jsonObject.get("starTime", Long.class);
        Integer bankId = jsonObject.get("bankId", Integer.class);
        starTime += 28800000;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(starTime / 1000, 0, ZoneOffset.UTC);
        log.debug("开始时间" + localDateTime);
        Boolean isNull = examService.IsExam(userDetails.getUsername(), bankId);
        if (isNull) {
            Integer i = answerService.post(userService.getUserByUsername(userDetails.getUsername()).getId(), answerList, bankId);
            if (i <= 0) {
                throw new ServiceException("数据库异常,请联系管理员");
            }
        } else {
            throw new ServiceException("对不起,你已经考过这门考试");
        }
        float sum = 0;  // 记录考试总分
        Answer[] answers = answerService.countScore(userService.getUserByUsername(userDetails.getUsername()).getId(), bankId);
        Question[] questions = questionService.countScore(bankId);
        Map<Integer, String> map = new ConcurrentHashMap<>();
        Map<Integer, Float> scoreMap = new ConcurrentHashMap<>();
        for (Question q : questions) {
            map.put(q.getId(), q.getSolution());
            scoreMap.put(q.getId(), q.getScore());
        }
        for (Answer a : answers) {
            String s = map.get(Integer.parseInt(a.getQuestionId()));
            if (a.getUserAnswer().equals(s)) {
                sum += scoreMap.get(Integer.parseInt(a.getQuestionId()));
            }
        }
        Integer i = examService.post(bankId, userDetails.getUsername(), localDateTime, sum);
        if (i <= 0) {
            throw new ServiceException("数据库异常,请联系管理员");
        }
        result.setCode(200);
        result.setMsg("考试完成");
        return result;
    }

    @GetMapping("/isExam")
    public Result isExam(@AuthenticationPrincipal UserDetails userDetails, Integer bankId) {
        Result result = new Result();
        Boolean aBoolean = examService.IsExam(userDetails.getUsername(), bankId);
        result.setCode(200);
        result.setData(aBoolean);
        return result;
    }

    @PostMapping("/deleteExam")
    @Transactional
    public void delete(@AuthenticationPrincipal UserDetails userDetails, Integer id) {
        examService.delete(userDetails.getUsername(), id);
        Integer userId = userService.getUserByUsername(userDetails.getUsername()).getId();
        answerService.deleteByUserId(userId, id);
    }

    @GetMapping("/myExam")
    public Result myExam(@AuthenticationPrincipal UserDetails userDetails, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        PageInfo<Exam> examPageInfo = examService.myExam(user, pageNum, pageSize);
        result.setSuccess(userDetails);
        result.setData(examPageInfo);
        return result;
    }
    @GetMapping("/myPassExam")
    public Result myPassExam(@AuthenticationPrincipal UserDetails userDetails, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        PageInfo<Exam> examPageInfo = examService.myPassExam(user, pageNum, pageSize);
        result.setSuccess(userDetails);
        result.setData(examPageInfo);
        return result;
    }
    @GetMapping("/passCheck")
    public Result passCheck(@AuthenticationPrincipal UserDetails userDetails) {
        Result result = new Result();
        Boolean aBoolean = examService.passCheck(userDetails.getUsername());
        result.setSuccess(userDetails);
        result.setData(aBoolean);
        return result;
    }

    @GetMapping("/oneDetail")
    public Result oneDetail(String username, Integer bankId) {
        Result result = new Result();
        Exam exam = examService.oneDetail(username, bankId);
        result.setCode(200);
        result.setData(exam);
        return result;
    }
}

