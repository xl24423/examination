package cn.xl.examination.service.impl;

import cn.xl.examination.dao.UserDao;
import cn.xl.examination.entity.Permission;
import cn.xl.examination.entity.Role;
import cn.xl.examination.entity.User;
import cn.xl.examination.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("用户名"+username);
        User user = userDao.selectByUserName(username);
        if (user == null) {
            throw new ServiceException("用户或密码错误");
        }
        if (user.getIsCheck()==0){
            throw new ServiceException("用户未审核");
        }
        if (user.getLocked()==0){
            throw new ServiceException("用户已锁定");
        }
        if (user.getEnabled()==0){
            throw new ServiceException("用户已被封禁");
        }
        List<Permission> permissions = userDao.selectPermissionByUserId(user.getId());
        String[] arr = new String[permissions.size()];
        int i = 0;
        for (Permission p : permissions) {
            arr[i] = p.getName();
            i++;
        }
        List<Role> roles = userDao.getUserRoleById(user.getId());
        arr = Arrays.copyOf(arr, arr.length + roles.size());
        for (Role role : roles){
            arr[i] = role.getName();
            i++;
        }
        UserDetails build = org.springframework.security.core.userdetails.User.builder().
                username(user.getUsername()).
                password(user.getPassword()).
                accountLocked(user.getLocked()==0).
                disabled(user.getEnabled()==0).
                authorities(arr).build();
        return build;
    }

}
