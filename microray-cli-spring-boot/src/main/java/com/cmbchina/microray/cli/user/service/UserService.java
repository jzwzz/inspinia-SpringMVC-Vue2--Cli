package com.cmbchina.microray.cli.user.service;

import com.cmbchina.microray.cli.auth.Credentials;
import com.cmbchina.microray.cli.auth.JwtUtil;
import com.cmbchina.microray.cli.auth.service.HttpCcmsService;
import com.cmbchina.microray.cli.common.ResponseResult;
import com.cmbchina.microray.cli.user.entity.Employee;
import com.cmbchina.microray.cli.user.entity.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private Logger logger = Logger.getLogger(UserService.class);
    public ResponseResult login(Credentials credentials) {

        ResponseResult responseResult = httpCcmsService.login(credentials.getUsername(), credentials.getPassword(),credentials.getSystemId());
        if (responseResult.isSuccess()) {
            Employee employee = (Employee) responseResult.getData();
            employee.addRole(new Role("USER","普通用户"));
            if (employee.getRoles() == null) {
                logger.warn("No role exist in system **" + credentials.getSystemId() + "** for user " + credentials.getUsername()+"grant role DEFAULT");
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

    public ResponseResult getUserInfoById(String userId) {
        ResponseResult responseResult = httpCcmsService.getEmployeeInfoById(userId);
        return responseResult;
    }
}
