package com.appsdeveloperblog.app.ws.security;

import com.appsdeveloperblog.app.ws.io.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.io.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private UserEntity userEntity;
    private String userId;
    public UserPrincipal(UserEntity userEntity){
        this.userEntity = userEntity;
        this.userId = userEntity.getUserId();
    }
    @Serial
    private static final long serialVersionUID = -7530187709860249942L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<AuthorityEntity> authorityEntities = new HashSet<>();

        //Get user roles
        Collection<RoleEntity> roles = userEntity.getRoles();
        if(roles.isEmpty()){
            return authorities;
        }

        roles.forEach((role)->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntities.addAll(role.getAuthorities());
        });

        authorityEntities.forEach((authorityEntity -> {
            authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
        }));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return this.userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        return userEntity.getEmailVerificationStatus();
    }
}
