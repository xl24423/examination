package cn.xl.examination.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.Score;
import cn.xl.examination.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Score)表控制层
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@RestController
@RequestMapping("score")
public class ScoreController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ScoreService scoreService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param score 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Score> page, Score score) {
        return success(this.scoreService.page(page, new QueryWrapper<>(score)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.scoreService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param score 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Score score) {
        return success(this.scoreService.save(score));
    }

    /**
     * 修改数据
     *
     * @param score 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Score score) {
        return success(this.scoreService.updateById(score));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.scoreService.removeByIds(idList));
    }
}

