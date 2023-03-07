package com.example.demo.services.serviceimpl;

import com.example.demo.services.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Override
    public String generateJwtToken(int id) {
       return Jwts.builder()
              .setSubject(id+"")
              .setIssuedAt(new Date())
              .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
              .signWith(SignatureAlgorithm.HS256, jwtSecret)
              .compact();
    }

    @Override
    public int isValidToken(String authToken) {
        return Integer.parseInt
               (Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().getSubject());
    }
}
