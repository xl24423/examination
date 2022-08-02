package cn.xl.examination.service.impl;

import cn.xl.examination.dao.CompanyDao;
import cn.xl.examination.entity.Company;
import cn.xl.examination.exception.ServiceException;
import cn.xl.examination.vo.RegisterVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xl.examination.dao.UserDao;
import cn.xl.examination.entity.User;
import cn.xl.examination.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * (User)表服务实现类
 *
 * @author xl
 * @since 2022-07-13 11:20:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;
    @Value("${dirPath}")
    private String path;

    @Override
    public void register(RegisterVO registerVO) throws IOException {
        if (!registerVO.getPassword().equals(registerVO.getConfirm())) {
            throw new ServiceException("两次密码不一致");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("invite_code", registerVO.getInviteCode());
        Company company = companyDao.selectOne(queryWrapper);
        if (company == null) {
            throw new ServiceException("邀请码不存在");
        }
        User user = userDao.selectByUserName(registerVO.getUsername());
        if (user != null) {
            throw new ServiceException("用户名已存在");
        }
        user = userDao.selectByPhone(registerVO.getTel());
        if (user != null) {
            throw new ServiceException("手机号已存在");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pwd = passwordEncoder.encode(registerVO.getPassword());
        String originalFilename = registerVO.getPicture().getOriginalFilename();
        String end = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newName = UUID.randomUUID() + end;
        registerVO.getPicture().transferTo(new File(path + "/" + newName));
        user = new User();
        user.setName(registerVO.getName()).
                setUsername(registerVO.getUsername()).
                setPassword(pwd).
                setCheck(0).   // 未审核为 0
                setCreatetime(LocalDateTime.now()).
                setCertificateUrl(path + "/" + newName).
                setInviteCode(registerVO.getInviteCode()).
                setCompanyId(company.getId()).
                setMajorType(registerVO.getMajorType()).
                setGender(registerVO.getGender()).
                setLocked(1).
                setEnabled(1).
                setRoleId(10);
        int insert = userDao.insert(user);
        if (insert != 1) {
            throw new ServerException("注册失败,数据库异常");
        }
    }

    @Override
    public PageInfo<User> getAllUser(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> allUser = userDao.findAllUser();
        PageInfo<User> userPageInfo = new PageInfo<>(allUser);
        return userPageInfo;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.selectByUserName(username);
    }
}

