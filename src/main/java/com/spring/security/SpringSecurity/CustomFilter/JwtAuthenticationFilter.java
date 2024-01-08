package com.spring.security.SpringSecurity.CustomFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.SpringSecurity.Entity.User;
import com.spring.security.SpringSecurity.Service.JWTService;
import com.spring.security.SpringSecurity.Service.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * TOKEN AUTHENTICATION FILTER CLASS, USE POST REQUEST
 *
 * */

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserDetailsService userDetailsService;
    private JWTService jwtService;

    public JwtAuthenticationFilter( UserDetailsService userDetailsService, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    private User user;
    private String username;
    private String password;

    /**
     * AUTHENTICATION INTENT
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {

            /** MAP THE JSON OBJECT TO JAVA OBJECT*/
            user = new ObjectMapper().readValue(request.getInputStream(),User.class);
            username = user.getUsername();
            password = user.getPassword();

        }catch (StreamReadException e){
            throw new RuntimeException(e);
        }catch (DatabindException e){
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);


        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    /**
     * WHEN THE AUTHENTICATION IS SUCCESS
     * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String token = this.jwtService.generateToken(user,300000L);

        response.addHeader("AUTHORIZATION: ",token);

        Map<String,Object> httpResponse = new HashMap<>();

        httpResponse.put("token: ",token);
        httpResponse.put("message: ","authentication correct");
        httpResponse.put("claims", jwtService.getSubject(token));

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
