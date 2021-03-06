package cn.xl.examination.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.Question;
import cn.xl.examination.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Question)表控制层
 *
 * @author makejava
 * @since 2022-08-02 16:16:11
 */
@RestController
@RequestMapping("question")
public class QuestionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private QuestionService questionService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param question 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Question> page, Question question) {
        return success(this.questionService.page(page, new QueryWrapper<>(question)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.questionService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param question 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Question question) {
        return success(this.questionService.save(question));
    }

    /**
     * 修改数据
     *
     * @param question 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Question question) {
        return success(this.questionService.updateById(question));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.questionService.removeByIds(idList));
    }
}

