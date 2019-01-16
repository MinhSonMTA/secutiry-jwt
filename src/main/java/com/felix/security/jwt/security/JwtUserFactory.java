package com.felix.security.jwt.security;

import com.felix.security.jwt.entity.UserAuthority;
import com.felix.security.jwt.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(UserInfo user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getUserAuthorities()),
                user.getEnabled(),
                user.getUpdateTime()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserAuthority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }
}