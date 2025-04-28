package com.sd.pms.auth.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUserName(String token);
    Claims extractAllClaims(String token);
    String generateToken(Map<String,Object> claims, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    Boolean isTokenValid(String token , UserDetails userDetails);
}
