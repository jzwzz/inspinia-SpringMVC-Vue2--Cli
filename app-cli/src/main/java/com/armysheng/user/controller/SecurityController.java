package com.armysheng.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/* * @Company: China Merchants Bank * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved. */
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
