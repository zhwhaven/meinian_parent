package com.haven.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.haven.pojo.Permission;
import com.haven.pojo.Role;
import com.haven.pojo.User;
import com.haven.service.UserService;
import org.apache.poi.hssf.record.ProtectionRev4Record;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SpringSecurityBean implements UserDetailsService {
    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.selectByUsername(s);
        System.out.println(user);
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> authorities=new HashSet<>();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
