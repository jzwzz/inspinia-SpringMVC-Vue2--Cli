package com.cmb.ccd.mr.rtm.guardian.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String employeeId;
    private String employeeName;
    public static final String DEFAULT_CREATOR_NAME = "system";
    public static final String DEFAULT_CREATOR_ID = "-123456";

    public static User system() {
        return new User(DEFAULT_CREATOR_ID, DEFAULT_CREATOR_NAME);
    }
}
