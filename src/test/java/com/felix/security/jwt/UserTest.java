package com.felix.security.jwt;

import com.felix.security.jwt.mapper.UserInfoMapper;
import com.felix.security.jwt.entity.UserInfo;
import com.felix.security.jwt.security.JwtTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author grez
 * @since 19-1-13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityJwtApp.class)
public class UserTest {

    @Autowired
    private PasswordEncoder passwordEncoderBean;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Test
    public void addUserTest() {
        String password = passwordEncoderBean.encode("admin");
        UserInfo user = new UserInfo();
        user.setUsername("admin");
        user.setPassword(password);
        user.setEnabled(true);
        user.setUpdateTime(new Date());

        userInfoMapper.insert(user);
    }

    @Test
    public void encodeJWT() {
        UserDetails admin = userDetailsService.loadUserByUsername("admin");
        System.out.println(admin);
        String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername("admin"));
        System.out.println(token);
        Boolean canlogin = jwtTokenUtil.validateToken(token, admin);
        System.out.println(canlogin);
    }

    @Test
    public void decodeJWT() {
        String s = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU0ODIyNTIwMiwiaWF0IjoxNTQ3NjIwNDAyfQ.X7yYFqnpqQpl8hhQqfz84JiUxga05kKDC0LsmDIM9vZBYfcMBIdSINPGnTYNNIxQouKIqppUOE9EY9H2pNVrxss6dOuDmQfhh27cyuS1OBL8qiMVWqqGzpBuQxc3-MCDHAFzyJbKY594XouYLccC9AjU1tR8Ww1hSV0sz9n20y1VvFYAbTIQHIuwlbpBqIa3ZfM8CvcLOtLX-L7sYJjk0lZO7rcn94fO50n0pT0aKx4NSm0X0m7oUDkUcfRW4WAcP1R7hmu_QOCcm42o9I5xjvnCGOGDLsXvJi7i32oGx-N6_-FVQvp0tlEV8ELTJ9AO3ZyPTd3zgCUQ3PL-1nEc-w";
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(s);
        Date issuedAtDateFromToken = jwtTokenUtil.getIssuedAtDateFromToken(s);
        Date expirationDateFromToken = jwtTokenUtil.getExpirationDateFromToken(s);
        System.out.println(usernameFromToken);
        ZoneId zone = ZoneId.systemDefault();
        System.out.println(LocalDateTime.ofInstant(issuedAtDateFromToken.toInstant(), zone));
        System.out.println(LocalDateTime.ofInstant(expirationDateFromToken.toInstant(), zone));

        Boolean aBoolean = jwtTokenUtil.canTokenBeRefreshed(s, new Date());
        System.out.println(aBoolean);
        s = jwtTokenUtil.refreshToken(s);
        usernameFromToken = jwtTokenUtil.getUsernameFromToken(s);
        issuedAtDateFromToken = jwtTokenUtil.getIssuedAtDateFromToken(s);
        expirationDateFromToken = jwtTokenUtil.getExpirationDateFromToken(s);
        System.out.println(usernameFromToken);
        System.out.println(LocalDateTime.ofInstant(issuedAtDateFromToken.toInstant(), zone));
        System.out.println(LocalDateTime.ofInstant(expirationDateFromToken.toInstant(), zone));
    }
}
