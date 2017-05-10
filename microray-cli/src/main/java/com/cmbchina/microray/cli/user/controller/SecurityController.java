package com.cmbchina.microray.cli.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sx2606
 * on 2017/5/3.
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    @RequestMapping(value = "user/info", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    String userInfo(String message) {
        return "user: " + message;
    }

    @RequestMapping(value = "admin/info", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    String adminInfo(String message) {
        return "admin: " + message;
    }
}
