package com.armysheng.user.entity;

import com.armysheng.common.utils.StringUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
@Data
public class User {
    private String userId;
    private String userName;
    private String token;
    private List<Role> roles = new ArrayList<>();

    public User addRole(Role role){
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
