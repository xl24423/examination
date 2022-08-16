package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.OperationTicketDao;
import cn.xl.examination.entity.OperationTicket;
import cn.xl.examination.service.OperationTicketService;
import org.springframework.stereotype.Service;

/**
 * (OperationTicket)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Service("operationTicketService")
public class OperationTicketServiceImpl extends ServiceImpl<OperationTicketDao, OperationTicket> implements OperationTicketService {

}

