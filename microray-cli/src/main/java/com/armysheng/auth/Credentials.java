package com.armysheng.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Credentials {

    private String username;
    private String password;
    private String systemId;
    private String roles;
}
