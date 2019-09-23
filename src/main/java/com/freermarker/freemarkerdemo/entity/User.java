package com.freermarker.freemarkerdemo.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class User implements UserDetails {

    private String id;

    private String username;

    private String password;

/*    private String authorities;*/

    private String role;

    private String rId;

    private Long lastPasswordChange;

    private Integer enable;

    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

    public User(String username, String password, Collection<? extends GrantedAuthority>  authorities, Long lastPasswordChange, Integer enable) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.lastPasswordChange = lastPasswordChange;
        this.enable = enable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
