package com.revaturemax.util;

import com.revaturemax.models.Employee;
import com.revaturemax.models.Role;
import com.revaturemax.models.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public final class Tokens {

    //private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    static String s = "Key temporarily hard coded so that restarting server does not reset key and therefore invalidate all tokens";
    private static final Key key = new SecretKeySpec(DatatypeConverter.parseBase64Binary(s), SignatureAlgorithm.HS256.getJcaName());

    public static String generateTokenString(Employee employee) {
        String token = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setId(employee.getId().toString())
                .setSubject(employee.getRole().toString())
                .signWith(key).compact();
        return token;
    }

    public static Token parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            String id = claims.getId();
            String role = claims.getSubject();
            if (id.matches("^\\d{1,19}$") && (role.equals("ASSOCIATE") || role.equals("INSTRUCTOR"))) {
                return new Token(Long.parseLong(id), Role.valueOf(role));
            }
            return null;
        } catch (SignatureException e) {
            return null;
        }
    }

}
