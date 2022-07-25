package cn.xl.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.CompanyDao;
import cn.xl.examination.entity.Company;
import cn.xl.examination.service.CompanyService;
import org.springframework.stereotype.Service;

/**
 * (Company)表服务实现类
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Service("companyService")
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, Company> implements CompanyService {

}

