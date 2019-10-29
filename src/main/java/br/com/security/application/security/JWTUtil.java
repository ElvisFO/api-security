package br.com.security.application.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Elvis Fernandes on 22/10/19
 */
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateTokenHeader(String username) {
        return Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                    .compact();
    }

    public Map<String, Object> generateTokenBody(String username) {

        Date expiration = new Date(System.currentTimeMillis() + this.expiration);
        Map<String, Object> map = new HashMap<>();
        map.put("token", this.generateToken(username, expiration));
        map.put("expiration", expiration);

        return map;
    }

    private String generateToken(String username, Date expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}
