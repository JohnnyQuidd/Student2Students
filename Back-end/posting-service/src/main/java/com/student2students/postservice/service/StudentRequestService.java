package com.student2students.postservice.service;

import com.student2students.postservice.jwt.JwtSecretKey;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class StudentRequestService {
    private final JwtSecretKey secretKey;

    @Autowired
    public StudentRequestService(JwtSecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String getStudentFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        for(Cookie currentCookie : cookies) {
            if(currentCookie.getName().equals("jwt"))
                token = currentCookie.getValue();
        }


        return Jwts.parser().setSigningKey(secretKey.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
