package com.cmbchina.microray.cli;

import com.cmbchina.microray.cli.auth.Credentials;
import com.cmbchina.microray.cli.auth.JwtUtil;
import org.junit.Test;

import java.util.Date;

public class ApiTest {

    @Test
    public void testJwt() {
        JwtUtil jwtUtil = new JwtUtil("mySecret");
        Credentials credentials = new Credentials();
        credentials.setRoles("ROLE_BDL01");
        credentials.setPassword("111");
        credentials.setUsername("ls");
        credentials.setAuthorizedAt(new Date().getTime());
        credentials.setSystemId("111");
        String token = jwtUtil.generateToken(credentials);
        System.out.println(token);
    }
}
