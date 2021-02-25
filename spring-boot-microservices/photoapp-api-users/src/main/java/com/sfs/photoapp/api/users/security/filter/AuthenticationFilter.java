package com.sfs.photoapp.api.users.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfs.photoapp.api.users.service.UsersService;
import com.sfs.photoapp.api.users.shared.UserDto;
import com.sfs.photoapp.api.users.ui.model.LoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UsersService usersService;
    private final Environment environment;

    public AuthenticationFilter(UsersService usersService, Environment environment, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            LoginRequestModel credentials = getLoginRequestModel(request);
            UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(credentials);
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        // generate jwt token and add to the http header
        String userName = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto = usersService.getUserDetailsByEmail(userName);

        String jwtsToken = getJwtsToken(userDto);
        response.addHeader("token", jwtsToken);
        response.addHeader("userId", userDto.getUserId());
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(LoginRequestModel credentials) {
        return new UsernamePasswordAuthenticationToken(
                credentials.getEmail(),
                credentials.getPassword(),
                new ArrayList<>()
        );
    }

    private LoginRequestModel getLoginRequestModel(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
    }

    private String getJwtsToken(UserDto userDto) {
        long tokenExpirationTime = Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time")));
        String tokenSecret = environment.getProperty("token.secret");

        return Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }
}
