package com.cmbchina.microray.cli.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class JwtUtil {

    private String secret;

    public JwtUtil(String secret) {
        this.secret = secret;
    }

    /**
     * Tries to parse specified String as a JWT token. If successful, returns
     * User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user
     * properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token
     * is invalid.
     */

    Credentials parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
            Credentials u = new Credentials();
            u.setUsername((String) body.get("username"));
            u.setPassword((String) body.get("password"));
            u.setRoles((String) body.get("roles"));
            u.setAuthorizedAt((Long) body.get("authorizedAt"));
            return u;
        } catch (JwtException | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role
     * as additional claims. These properties are taken from the specified User
     * object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(Credentials u) {
        Claims claims = Jwts.claims();
        claims.put("username", u.getUsername());
        claims.put("password", u.getPassword());
        claims.put("roles", u.getRoles());
        claims.put("authorizedAt", new Date().getTime());

        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil("microray-cli-secret");
        Credentials user = new Credentials();
        user.setUsername("671852");
        user.setPassword("111");
        user.setRoles("ROLE_USER");
        String token = jwtUtil.generateToken(user);
        System.out.println(token);

        Credentials credentials = jwtUtil.parseToken(token);
        System.out.println(credentials);

    }

    public Boolean validateToken(String token) {
        final Long authorizedAt = parseToken(token).getAuthorizedAt();
        return !isTokenExpired(authorizedAt);
    }

    private boolean isTokenExpired(Long authorizedAt) {
        return new Date().getTime() - authorizedAt > 2 * 60 * 60 * 1000;
    }
}
