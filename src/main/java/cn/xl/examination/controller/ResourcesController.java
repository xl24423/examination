package cn.xl.examination.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.Resources;
import cn.xl.examination.service.ResourcesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Resources)表控制层
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@RestController
@RequestMapping("resources")
public class ResourcesController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ResourcesService resourcesService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param resources 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Resources> page, Resources resources) {
        return success(this.resourcesService.page(page, new QueryWrapper<>(resources)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.resourcesService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param resources 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Resources resources) {
        return success(this.resourcesService.save(resources));
    }

    /**
     * 修改数据
     *
     * @param resources 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Resources resources) {
        return success(this.resourcesService.updateById(resources));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.resourcesService.removeByIds(idList));
    }
}

