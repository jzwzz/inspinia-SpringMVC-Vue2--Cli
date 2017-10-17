package com.cmbchina.microray.cli.auth;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMalformedException extends AuthenticationException {

    public JwtTokenMalformedException(String msg) {
        super(msg);
    }
}