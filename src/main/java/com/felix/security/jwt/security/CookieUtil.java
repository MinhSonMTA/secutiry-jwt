package com.felix.security.jwt.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author bingo
 */
@Component
public class CookieUtil {

    @Value("${jwt.expiration}")
    private Long expiration;

    public void delCookie(HttpServletRequest request, String path, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath(path);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * @param response
     * @param name
     * @param value
     * @throws UnsupportedEncodingException
     */
    public void addCookie(HttpServletResponse response, String path, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiration.intValue());
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    public String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}