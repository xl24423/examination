package cn.xl.examination.controller;



import cn.xl.examination.common.lang.Result;
import cn.xl.examination.entity.User;
import cn.xl.examination.exception.ServiceException;
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
    public PageInfo<QuestionBank> getAllQuestionBank(Integer pageNum, Integer pageSize){
        return questionBankService.getAllQuestionBank(pageNum,pageSize);
    }


    /**
     * 修改数据
     *
     * @param questionBank 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody QuestionBank questionBank) {
        return success(this.questionBankService.updateById(questionBank));
    }

    @DeleteMapping
    public Result delete(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("id") Integer id,Integer pageNum, Integer pageSize) {
        Result result = new Result();
        User user = userService.getUserByUsername(userDetails.getUsername());
        if (!user.getRoleId().equals("1")){
            result.setAuthError();
            return result;
        }
        if (id == null){
            result.setPathError();
            return result;
        }
        Integer integer = questionBankService.deleteOne(id);
        if (integer != 1){
            result.setDataBaseError();
            return result;
        }
        result.setSuccess(userDetails);
        result.setMsg("删除成功");
        result.setData(questionBankService.getAllQuestionBank(pageNum,pageSize));
        return result;
    }
}

