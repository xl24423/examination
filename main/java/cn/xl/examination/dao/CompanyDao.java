package cn.xl.examination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.xl.examination.entity.Company;
import org.springframework.stereotype.Repository;

/**
 * (Company)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-13 10:55:57
 */
@Repository
public interface CompanyDao extends BaseMapper<Company> {

}

