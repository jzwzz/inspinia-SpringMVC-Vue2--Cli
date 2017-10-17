package com.cmbchina.microray.cli.auth;

import org.springframework.security.core.AuthenticationException;

class JwtTokenMissingException extends AuthenticationException {

    JwtTokenMissingException(String msg) {
        super(msg);
    }
}
