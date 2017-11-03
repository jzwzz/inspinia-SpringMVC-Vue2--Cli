package com.cmb.ccd.mr.rtm.guardian.user.service;

import com.cmb.ccd.mr.rtm.guardian.auth.Credentials;
import com.cmb.ccd.mr.rtm.guardian.auth.JwtUtil;
import com.cmb.ccd.mr.rtm.guardian.auth.service.HttpCcmsService;
import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.cmb.ccd.mr.rtm.guardian.user.entity.Employee;
import com.cmb.ccd.mr.rtm.guardian.user.entity.Role;
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
        ResponseResult responseResult = httpCcmsService.login(credentials.getUsername(), credentials.getPassword(), credentials.getSystemId());
        if (responseResult.isSuccess()) {
            Employee employee = (Employee) responseResult.getData();
            employee.addRole(new Role("USER", "普通用户"));
            if (employee.getRoles() == null) {
                logger.warn("No role exist in system **" + credentials.getSystemId() + "** for user " + credentials.getUsername() + "grant role DEFAULT");
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

}
