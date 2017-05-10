package com.cmbchina.microray.cli.auth.service;

import com.cmbchina.microray.cli.common.ResponseResult;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
public interface HttpCcmsService {

    ResponseResult login(String username, String password);

    ResponseResult getEmployeeInfoById(String employeeId);

    String getRedirectPath(String redirectUrl);
}
