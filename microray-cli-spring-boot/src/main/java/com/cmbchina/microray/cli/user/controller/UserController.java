package com.cmbchina.microray.cli.user.controller;

import com.cmbchina.microray.cli.auth.Credentials;
import com.cmbchina.microray.cli.auth.JwtUtil;
import com.cmbchina.microray.cli.common.ResponseResult;
import com.cmbchina.microray.cli.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService userService;
    private Logger logger = Logger.getLogger(UserController.class.getName());


    @RequestMapping(value = "/login", method = {RequestMethod.POST,
            RequestMethod.POST}, produces = "application/json;charset=utf-8")
    ResponseResult login(Credentials credentials) {

        return userService.login(credentials);
    }

    @RequestMapping(value = "security", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    String getUserInfoWithToken(String message) {
        return "security: " + message;
    }

    @RequestMapping(value = "unsafe", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    String getUserInfoWithoutToken(String message) {
        return "unsafe: " + message;
    }
}
