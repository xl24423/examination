package cn.xl.examination.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.Solution;
import cn.xl.examination.service.SolutionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Solution)表控制层
 *
 * @author makejava
 * @since 2022-07-13 10:55:58
 */
@RestController
@RequestMapping("solution")
public class SolutionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SolutionService solutionService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param solution 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Solution> page, Solution solution) {
        return success(this.solutionService.page(page, new QueryWrapper<>(solution)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.solutionService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param solution 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Solution solution) {
        return success(this.solutionService.save(solution));
    }

    /**
     * 修改数据
     *
     * @param solution 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Solution solution) {
        return success(this.solutionService.updateById(solution));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.solutionService.removeByIds(idList));
    }
}

