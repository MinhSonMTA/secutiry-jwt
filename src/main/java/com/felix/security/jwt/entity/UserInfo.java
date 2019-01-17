package com.felix.security.jwt.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


/**
 * @author felix-ma
 * @create 2019/1/17 18:02
 **/
@TableName("user_info")
public class UserInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Date updateTime;

    private JSONObject additionalInfo;

    private List<UserAuthority> userAuthorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(List<UserAuthority> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    public JSONObject getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(JSONObject additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
