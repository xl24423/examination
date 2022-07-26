package cn.xl.examination.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.Exam;
import cn.xl.examination.service.ExamService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Exam)表控制层
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@RestController
@RequestMapping("/exam")
public class ExamController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ExamService examService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param exam 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Exam> page, Exam exam) {
        return success(this.examService.page(page, new QueryWrapper<>(exam)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.examService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param exam 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Exam exam) {
        return success(this.examService.save(exam));
    }

    /**
     * 修改数据
     *
     * @param exam 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Exam exam) {
        return success(this.examService.updateById(exam));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.examService.removeByIds(idList));
    }

    @GetMapping("/test")
    public String t(){
        return "success()";
    }
}

