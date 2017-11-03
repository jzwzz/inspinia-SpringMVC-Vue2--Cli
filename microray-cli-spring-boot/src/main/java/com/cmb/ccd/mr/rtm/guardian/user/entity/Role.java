package com.cmb.ccd.mr.rtm.guardian.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
@Data
@AllArgsConstructor
public class Role {
    private String roleId;
    private String roleName;

    public String getRoleId() {
        if (roleId.startsWith("ROLE_")) {
            return roleId;
        } else {
            return "ROLE_" + roleId;
        }
    }

}
