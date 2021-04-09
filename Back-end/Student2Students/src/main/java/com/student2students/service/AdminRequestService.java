package com.student2students.service;

import com.student2students.jwt.JwtSecretKey;
import com.student2students.model.Admin;
import com.student2students.repository.AdminRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class AdminRequestService {
    private final AdminRepository adminRepository;
    private final JwtSecretKey jwtSecretKey;

    @Autowired
    public AdminRequestService(AdminRepository adminRepository, JwtSecretKey jwtSecretKey) {
        this.adminRepository = adminRepository;
        this.jwtSecretKey = jwtSecretKey;
    }

    public Admin getStudentFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        for(Cookie currentCookie : cookies) {
            if(currentCookie.getName().equals("jwt"))
                token = currentCookie.getValue();
        }

        String username = Jwts.parser().setSigningKey(jwtSecretKey.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return adminRepository.findByUsername(username);
    }
}
