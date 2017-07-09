package com.armysheng.auth;

import org.springframework.security.core.AuthenticationException;

class JwtTokenMissingException extends AuthenticationException {


    JwtTokenMissingException(String msg) {
        super(msg);
    }
}
