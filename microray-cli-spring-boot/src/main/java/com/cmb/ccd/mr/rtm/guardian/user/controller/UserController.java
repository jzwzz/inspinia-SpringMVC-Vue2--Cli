package com.cmb.ccd.mr.rtm.guardian.user.controller;

import com.cmb.ccd.mr.rtm.guardian.auth.Credentials;
import com.cmb.ccd.mr.rtm.guardian.auth.service.HttpCcmsService;
import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.cmb.ccd.mr.rtm.guardian.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpCcmsService httpCcmsService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=utf-8")
    public ResponseResult login(Credentials credentials) {
        return userService.login(credentials);
    }

    @PreAuthorize("hasRole('ROLE_RMG01')")
    @RequestMapping(value = "security", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public String getUserInfoWithToken(String message) {
        return "security: " + message;
    }

    @PreAuthorize("hasRole('ROLE_SS')")
    @RequestMapping(value = "unsafe", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public String getUserInfoWithoutToken(String message) {
        return "unsafe: " + message;
    }

    @RequestMapping(value = "list")
    public ResponseResult getUserByRoleId(String roleId) {
        return httpCcmsService.getUserByRoleId(roleId);
    }
}
