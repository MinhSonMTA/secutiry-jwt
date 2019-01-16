package com.felix.security.jwt.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.felix.security.jwt.entity.UserInfo;
import com.felix.security.jwt.security.vo.UserInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author grez
 * @date 19-1-13
 **/
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<UserInfoVo> findByUsername(String username);
}
