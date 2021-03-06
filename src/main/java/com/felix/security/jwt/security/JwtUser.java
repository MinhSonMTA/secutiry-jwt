package com.felix.security.jwt.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String audience;
    private final boolean enabled;
    private final Date updateTime;

    public JwtUser(
            Long id,
            String username,
            String password, Collection<? extends GrantedAuthority> authorities,
            boolean enabled,
            Date updateTime,
            String audience
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.updateTime = updateTime;
        this.audience = audience;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getUpdateTime() {
        return updateTime;
    }

    public String getAudience() {
        return audience;
    }
}
