/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.auth.service;

import com.cmb.ccd.mr.rtm.guardian.common.ResponseResult;
import com.cmb.ccd.mr.rtm.guardian.user.entity.Employee;
import com.cmb.ccd.mr.rtm.guardian.user.entity.User;

import java.util.List;

public interface HttpCcmsService {

    ResponseResult login(String username, String password, String systemId);

    ResponseResult getEmployeeInfoById(String employeeId);

    ResponseResult<List<User>> getUserByRoleId(String roleId);
}
