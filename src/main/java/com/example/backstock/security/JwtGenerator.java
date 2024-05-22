package com.example.backstock.security;


import com.example.backstock.dto.AuthResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtGenerator {

    @Value("${access.token.expiration}")
    private Long accessTokenExpiration;

    @Value("${refresh.token.expiration}")
    private Long refreshTokenExpiration;
    @Value("${access.token.secret}")
    private String accessTokenSecret;
    @Value("${refresh.token.secret}")
    private String refreshTokenSecret;


    public AuthResponseDto generateTokens(Authentication authentication) {
        String userName = authentication.getName();
        Date currentDate = new Date();
        String accessToken = this.generateAccessToken(authentication, userName, currentDate);
        String refreshToken = this.generateRefreshToken(userName, currentDate);
        return new AuthResponseDto(accessToken, refreshToken, "Logged in successfully");
    }

    public String generateAccessToken(Authentication authentication, String userName, Date currentDate) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, accessTokenSecret)
                .compact();
    }

    private String generateRefreshToken(String userName, Date currentDate) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, refreshTokenSecret)
                .compact();
    }


    public String getUsernameFromToken(String token, String tokenSecret) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token, String tokenSecret) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getJWTFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}

