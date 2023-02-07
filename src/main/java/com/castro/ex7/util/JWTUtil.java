package com.castro.ex7.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {
        @Value("${org.castro.jwt.secretkey}")
        private String secretKey;
        private long expire = 60*24*30;
        public String generateToken(String content) throws Exception {
                String str = Jwts.builder()
                        .setIssuedAt(new Date())
                        .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                        .claim("sub", content)
                        .signWith(SignatureAlgorithm.ES256, secretKey.getBytes("UTF-8"))
                        .compact();
                return str;
        }

        public String validateAndExtract(String tokenStr) throws Exception{
                String contentValue = null;

                try {
                        DefaultJws defaultJws = (DefaultJws) Jwts.parser()
                                .setSigningKey(secretKey.getBytes("UTF-8"))
                                .parseClaimsJws(tokenStr);
                        DefaultClaims claims = (DefaultClaims)  defaultJws.getBody();
                        contentValue = claims.getSubject();

                }catch (Exception e){
                        e.printStackTrace();
                        contentValue = null;
                }
                return contentValue;
        }


}
