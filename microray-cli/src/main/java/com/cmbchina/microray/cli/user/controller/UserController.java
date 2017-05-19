package com.cmbchina.microray.cli.user.controller;

import com.cmbchina.microray.cli.auth.Credentials;
import com.cmbchina.microray.cli.auth.JwtUtil;
import com.cmbchina.microray.cli.common.ResponseConstants;
import com.cmbchina.microray.cli.common.ResponseResult;
import com.cmbchina.microray.cli.user.entity.Employee;
import com.cmbchina.microray.cli.user.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
@Log4j

@CrossOrigin
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    ResponseResult login(Credentials credentials) {

        return userService.login(credentials);
    }

    @RequestMapping(value = "security", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    String getUserInfoWithToken(String message) {
        return "security: " + message;
    }

    @RequestMapping(value = "unsafe", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    String getUserInfoWithoutToken(String message) {
        return "unsafe: " + message;
    }

    //    for cas login，delete if use CCMS api login
    @RequestMapping(value = "casLogin")
    public String casLogin(@RequestParam("redirectUrl") String url) {
        Employee employee = userService.checkLogin();

        if (employee != null) {
            return "redirect:" + url+"/"+employee.getToken();
        }
        return "redirect:" + userService.getCasRedirectUrl(url);
    }

    @RequestMapping(value = "loginStatus",method = RequestMethod.POST)
    public @ResponseBody
    ResponseResult loginStatus(@RequestParam String  token) {
        Employee employee = userService.validateToken(token);
        if (employee != null) {
            return new ResponseResult(employee);
        } else
            return new ResponseResult(ResponseConstants.INTERNAL_ERROR, "backend not logged in or token invalid");

    }
}
