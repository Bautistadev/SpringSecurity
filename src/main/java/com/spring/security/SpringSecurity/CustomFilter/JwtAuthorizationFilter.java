package com.spring.security.SpringSecurity.CustomFilter;

import com.spring.security.SpringSecurity.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 *
 * AFTER AUTHENTICATION, IS NECESSARY DEFINE THEN AUTHORIZATION
 *
 *
 *
 * */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;
        UserDetails userDetails = null;

        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            /**DELETE BEARE */


            token = tokenHeader.substring(7,tokenHeader.length());
            username = this.jwtService.getSubject(token);
            userDetails = this.userDetailsService.loadUserByUsername(username);


            System.out.println("ES VALIDO: "+this.jwtService.isValid(token,userDetails));
            if(this.jwtService.isValid(token,userDetails)){
                System.out.println("ES BIEN  VALIDO --?>" + this.jwtService.isValid(token,userDetails));
                System.out.println(userDetails.getAuthorities());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(request,response);


    }
}
