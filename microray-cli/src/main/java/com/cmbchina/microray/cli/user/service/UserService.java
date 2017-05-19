package com.cmbchina.microray.cli.user.service;

import com.cmbchina.microray.cli.auth.Credentials;
import com.cmbchina.microray.cli.auth.JwtUtil;
import com.cmbchina.microray.cli.auth.service.HttpCcmsService;
import com.cmbchina.microray.cli.common.ResponseResult;
import com.cmbchina.microray.cli.user.entity.Employee;
import com.cmbchina.microray.cli.user.entity.Role;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
@Service
public class UserService {
    @Autowired
    HttpCcmsService httpCcmsService;

    @Autowired
    JwtUtil jwtUtil;
    @Value("${app.name}")
    private String systemId;


    private Logger logger = Logger.getLogger(UserService.class);

    public ResponseResult login(Credentials credentials) {
        ResponseResult responseResult = httpCcmsService.login(credentials.getUsername(), credentials.getPassword());
        if (responseResult.isSuccess()) {
            Employee employee = (Employee) responseResult.getData();
            employee.addRole(new Role("USER", "普通用户"));
            if (employee.getRoles() == null) {
                logger.warn("No role exist in system **" + systemId + "** for user " + credentials.getUsername() + "grant role DEFAULT");
            } else {
                credentials.setRoles(employee.getCommaDelimitedRoleIds());
            }
            String token = jwtUtil.generateToken(credentials);
            employee.setToken(token);
            return new ResponseResult<>(employee);
        } else {
            return responseResult;
        }
    }

    public String getCasRedirectUrl(String redirectUrl) {
        return httpCcmsService.getRedirectPath(redirectUrl);
    }

    public ResponseResult getUserInfoById(String userId) {
        ResponseResult responseResult = httpCcmsService.getEmployeeInfoById(userId);
        return responseResult;
    }

    public Employee checkLogin() {
        if (AssertionHolder.getAssertion() == null
                || AssertionHolder.getAssertion().getPrincipal() == null
                || AssertionHolder.getAssertion().getPrincipal().getAttributes() == null
                || AssertionHolder.getAssertion().getPrincipal().getAttributes().get("user") == null) {
            System.out.println("no user in session");
            return null;
        }
        return initUser();
    }

    private Employee initUser() {
        String userJson = (String) AssertionHolder.getAssertion().getPrincipal().getAttributes().get("user");
        Employee user = new Gson().fromJson(userJson, Employee.class);
        List<Role> newRoles = user.getRoles().stream().filter(role -> role.getSystemId().equals(systemId)).collect(Collectors.toCollection(ArrayList::new));
        user.setRoles(newRoles);
        user.addRole(new Role("USER", "普通用户"));
        Credentials credentials = new Credentials(user.getEmployeeId(), "password", user.getCommaDelimitedRoleIds());
        String token = jwtUtil.generateToken(credentials);
        user.setToken(token);
        return user;
    }

    //    get employee from valid token
    public Employee validateToken(String token) {
        Credentials credentials = jwtUtil.parseToken(token);
        if (credentials == null) {
            return null;
        }
        ResponseResult responseResult = getUserInfoById(credentials.getUsername());
        if (!responseResult.isSuccess()) {
            return null;
        } else {
            Employee employee = (Employee) responseResult.getData();
            employee.addRole(new Role("USER", "普通用户"));
            if (employee.getRoles() == null) {
                logger.warn("No role exist in system **" + systemId + "** for user " + credentials.getUsername() + "grant role DEFAULT");
            } else {
                credentials.setRoles(employee.getCommaDelimitedRoleIds());
            }
            employee.setToken(token);
            return employee;
        }
    }
}
