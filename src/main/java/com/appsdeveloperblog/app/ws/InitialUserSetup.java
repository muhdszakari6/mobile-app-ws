package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.io.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.io.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.repository.AuthorityRepository;
import com.appsdeveloperblog.app.ws.repository.RoleRepository;
import com.appsdeveloperblog.app.ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.dto.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Component
public class InitialUserSetup {
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event){
        System.out.println("From application ready event");
        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if(roleAdmin==null){
            return;
        }

        UserEntity adminUser = new UserEntity();
        adminUser.setFirstName("Salim");
        adminUser.setLastName("Zakari");
        adminUser.setEmail("salimzakari600@gmail.com");
        adminUser.setUserId(utils.generateUserId(30));
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
        adminUser.setRoles(Arrays.asList(roleAdmin));
        UserEntity user = userRepository.findByEmail("salimzakari600@gmail.com");
        if(user==null){
            userRepository.save(adminUser);
        }
    }

    @Transactional
    AuthorityEntity createAuthority(String name){
        AuthorityEntity authority = authorityRepository.findByName(name);
        if(authority==null){
            authority = new AuthorityEntity();
            authority.setName(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    RoleEntity createRole(
            String name,
            Collection<AuthorityEntity> authorities
    ){
        RoleEntity role = roleRepository.findByName(name);

        if(role==null){
            role = new RoleEntity();
            role.setName(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
}
