package com.cmbchina.microray.cli.user.entity;

import com.cmbchina.microray.cli.common.utils.StringUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
@Data
public class Employee {
    private String employeeId;
    private String employeeName;
    private String unitId;
    private String unitName;
    private String departmentId;
    private String departmentName;
    private String token;
    private List<Role> roles = new ArrayList<>();

    public Employee addRole(Role role){
        this.roles.add(role);
        return this;
    }

    public String getCommaDelimitedRoleIds(){
        ArrayList<String> roleIds = new ArrayList<>();
        for (Role role :roles){
            roleIds.add(role.getRoleId());
        }
        return StringUtil.flat(roleIds,",","","");
    }
}
