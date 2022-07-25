package cn.xl.examination.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.xl.examination.entity.OperationTicket;
import cn.xl.examination.service.OperationTicketService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (OperationTicket)表控制层
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@RestController
@RequestMapping("operationTicket")
public class OperationTicketController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private OperationTicketService operationTicketService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param operationTicket 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<OperationTicket> page, OperationTicket operationTicket) {
        return success(this.operationTicketService.page(page, new QueryWrapper<>(operationTicket)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.operationTicketService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param operationTicket 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody OperationTicket operationTicket) {
        return success(this.operationTicketService.save(operationTicket));
    }

    /**
     * 修改数据
     *
     * @param operationTicket 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody OperationTicket operationTicket) {
        return success(this.operationTicketService.updateById(operationTicket));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.operationTicketService.removeByIds(idList));
    }
}

