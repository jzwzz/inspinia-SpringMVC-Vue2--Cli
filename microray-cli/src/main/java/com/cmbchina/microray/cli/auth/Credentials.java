package com.cmbchina.microray.cli.auth;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Credentials {

    private String username;
    private String password;
    private String roles;
}
