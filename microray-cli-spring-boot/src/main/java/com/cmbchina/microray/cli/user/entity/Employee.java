package com.cmbchina.microray.cli.user.entity;

import lombok.Data;
import org.springframework.util.StringUtils;

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

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public String getCommaDelimitedRoleIds() {
        ArrayList<String> roleIds = new ArrayList<>();
        for (Role role : roles) {
            roleIds.add(role.getRoleId());
        }
        return StringUtils.collectionToCommaDelimitedString(roleIds);
    }
}
