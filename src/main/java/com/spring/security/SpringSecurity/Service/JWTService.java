package com.spring.security.SpringSecurity.Service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    private JWTUtils jwtUtils;

    public JWTService(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public String generateToken(UserDetails userDetails, Long timeExpired){
        return jwtUtils.generateToken(userDetails,timeExpired);
    }

    public Claims getClaims(String token){
        return this.jwtUtils.getAllClaimsFromToken(token);
    }
    public String getSubject(String token){
        return this.jwtUtils.getObjectFromToken(token);
    }
    public Date getIssuedAt(String token){
        return this.jwtUtils.getIssuedAt(token);
    }
    public Date getExpired(String token){
        return this.jwtUtils.getExpiration(token);
    }
    public Boolean isExpired(String token){
        return this.jwtUtils.isTokenExpired(token);
    }
    public Boolean isValid(String token,UserDetails userDetails){
        return this.jwtUtils.validateToken(token,userDetails);
    }

}
