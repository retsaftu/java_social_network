package com.example.social_network.helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Token {
    public final static long EXPIRATION_TIME = 864_000_000;
    public final static String SECRET = "Secret";

    public static String getToken(String token) {
        System.out.println("token");
        return Jwts.builder().setSubject(token)
                .setExpiration(new Date(EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public static String getValueByToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }
}
