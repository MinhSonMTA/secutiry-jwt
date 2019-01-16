package com.felix.security.jwt.security.service;

import com.felix.security.jwt.mapper.UserInfoMapper;
import com.felix.security.jwt.entity.UserAuthority;
import com.felix.security.jwt.entity.UserInfo;
import com.felix.security.jwt.security.JwtUserFactory;
import com.felix.security.jwt.security.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserInfoVo> userInfoVos = userInfoMapper.findByUsername(username);
        UserInfo user = new UserInfo();
        if (userInfoVos == null || userInfoVos.size() == 0) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            // 加载用户权限
            List<UserAuthority> userAuthorities = new ArrayList<>();
            for (UserInfoVo userInfoVo : userInfoVos) {
                UserAuthority userAuthority = new UserAuthority();
                userAuthority.setId(userInfoVo.getAuthorityId());
                userAuthority.setName(userInfoVo.getName());
                userAuthorities.add(userAuthority);
            }
            // 加载用户登陆
            Long id = userInfoVos.get(0).getId();
            System.out.println(id);
            user.setId(userInfoVos.get(0).getId());
            user.setUsername(userInfoVos.get(0).getUsername());
            user.setPassword(userInfoVos.get(0).getPassword());
            user.setEnabled(userInfoVos.get(0).getEnabled());
            user.setUserAuthorities(userAuthorities);
        }
        return JwtUserFactory.create(user);
    }
}
