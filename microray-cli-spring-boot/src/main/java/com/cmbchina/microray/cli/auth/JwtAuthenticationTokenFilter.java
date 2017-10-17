package com.cmbchina.microray.cli.auth;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(this.tokenHeader);

        String username = null;
        String authToken = null;
        Credentials parsedUser = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                parsedUser = jwtUtil.parseToken(authToken);
                username = parsedUser.getUsername();
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        logger.info("checking authentication for user " + username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtUtil.validateToken(authToken)) {
                UserDetails userDetails = retrieveUser(parsedUser);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    private UserDetails retrieveUser(Credentials parsedUser) throws AuthenticationException {

        if (parsedUser == null) {
            throw new JwtTokenMalformedException("JWT token is not valid");
        }

        List<GrantedAuthority> authorityList = AuthorityUtils
                .commaSeparatedStringToAuthorityList(parsedUser.getRoles());
        logger.info(authorityList);
        return new User(parsedUser.getUsername()
                , parsedUser.getPassword(), authorityList);
    }
}
