package com.spring.security.SpringSecurity.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JWTUtils {

    @Value("${jwt.secret.key}")
    private String key;

    private Long TIME_TOKEN_VALID=30*60*1000L;

    private Long TIME_TOKE_REFRESH=30*60*60*1000l;

    //CREATE TOKEN
    public String doGenerateJWT(Map<String, Object> Claims,String subject,Long timeExp){
        String jwt = Jwts.builder()
                .setClaims(Claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+timeExp))
                .signWith(SignatureAlgorithm.HS512,key)
                .compact();

        return jwt;
    }



    /**
     * RETURN ALL CLAIMS
     * */
    public Claims getAllClaimsFromToken(String token){
        System.out.println("entrsa");
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }



    public <T> T getClamFromToken(String token, Function<Claims,T> claimsResolve){
        /*GIVE AL TOKEN*/
        final Claims claim = getAllClaimsFromToken(token);
        System.out.println("TOKEN"+getAllClaimsFromToken(token).toString());
        return claimsResolve.apply(claim);
    }

    public String getObjectFromToken(String token){
        return getClamFromToken(token,Claims::getSubject);
    }

    public Date getIssuedAt(String token){
        return getClamFromToken(token,Claims::getIssuedAt);
    }
    public Date getExpiration(String token){
        return getClamFromToken(token,Claims::getExpiration);
    }


    public boolean isTokenExpired(String token){
        Date time = getExpiration(token);
        return time.before(new Date());
    }

    public String generateToken(UserDetails userDetails,Long timeExpired){
        Map<String,Object> claims = new HashMap<>();
        claims.put("Rol",userDetails.getAuthorities());
        return doGenerateJWT(claims, userDetails.getUsername(), timeExpired);
    }

    public String generateToken(UserDetails userDetails,Map<String,Object> Claims,Long timeExpired){
        System.out.println("geenrar token -->"+userDetails.getUsername());
        return doGenerateJWT(Claims, userDetails.getUsername(),timeExpired);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        String userName = this.getObjectFromToken(token);
        return userName.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }


}
