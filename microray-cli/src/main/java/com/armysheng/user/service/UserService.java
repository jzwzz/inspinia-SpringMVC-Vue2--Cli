package com.armysheng.user.service;

import com.armysheng.auth.Credentials;
import com.armysheng.user.entity.Role;
import com.armysheng.user.entity.User;
import com.armysheng.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
@Service
public class UserService {

    @Autowired
    JwtUtil jwtUtil;

    public User login(Credentials credentials) {
        User user = new User();
        if(credentials.getUsername().equalsIgnoreCase("admin")){
            user.setUserId(credentials.getUsername());
            user.setUserName("armysheng");
            user.addRole(new Role("ADMIN","管理员"));
            user.addRole(new Role("USER","普通用户"));
            credentials.setRoles(user.getCommaDelimitedRoleIds());
            String token = jwtUtil.generateToken(credentials);
            user.setToken(token);
        }else {
            user.setUserId(credentials.getUsername());
            user.setUserName(credentials.getUsername());
            user.addRole(new Role("USER","普通用户"));
            credentials.setRoles(user.getCommaDelimitedRoleIds());
            String token = jwtUtil.generateToken(credentials);
            user.setToken(token);
        }
        return user;
    }
}
