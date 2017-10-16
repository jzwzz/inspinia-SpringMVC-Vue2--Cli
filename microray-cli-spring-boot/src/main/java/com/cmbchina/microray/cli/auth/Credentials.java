package com.cmbchina.microray.cli.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class Credentials {

    private String username;
    private String password;
    private String systemId;
    private String roles;
    private Long authorizedAt;
}
