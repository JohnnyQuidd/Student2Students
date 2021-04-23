package com.zuul.util;

import com.zuul.jwt.JwtSecretKey;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class Authorization {
    private final JwtSecretKey secretKey;

    @Autowired
    public Authorization(JwtSecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String getStudentFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        for(Cookie currentCookie : cookies) {
            if(currentCookie.getName().equals("jwt"))
                token = currentCookie.getValue();
        }

        String username = Jwts.parser().setSigningKey(secretKey.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if(username == null ||  username.equals("")) {
            throw new IllegalStateException("");

        }

        return username;
    }
}
