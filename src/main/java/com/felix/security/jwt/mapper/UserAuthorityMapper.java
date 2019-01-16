package com.felix.security.jwt.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.felix.security.jwt.entity.UserAuthority;

/**
 * @author grez
 * @since 19-1-13
 **/
public interface UserAuthorityMapper extends BaseMapper<UserAuthority> {

    UserAuthority findUserAuthorityById(Long id);

}
