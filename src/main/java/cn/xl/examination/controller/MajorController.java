package cn.xl.examination.controller;


import cn.xl.examination.common.lang.Result;
import cn.xl.examination.entity.Major;
import cn.xl.examination.service.MajorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Major)表控制层
 *
 * @author makejava
 * @since 2022-08-30 11:18:31
 */
@RestController
@RequestMapping("/major")
public class MajorController extends ApiController {
    @Autowired
    MajorService majorService;
    @GetMapping("/allMajor")
    public Result allMajor(){
        Result result = new Result();
        List<Major> majors = majorService.getAllMajor();
        if (majors == null || majors.size() == 0){
            result.setDataBaseError();
            return result;
        }
        result.setCode(200);
        result.setData(majors);
        return result;
    }
}

