package com.spring.security.SpringSecurity.Service;

import com.spring.security.SpringSecurity.Entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsServiceImplements implements UserDetailsService {


    private UserService userService;

    public UserDetailsServiceImplements(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userdb = this.userService.findUserByUserName(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userdb.getRol().name()));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userdb.getUsername(),userdb.getPassword(),authorities);
        return userDetails;
    }
}
