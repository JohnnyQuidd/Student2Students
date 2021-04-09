package com.student2students.service;

import com.student2students.jwt.JwtSecretKey;
import com.student2students.model.Student;
import com.student2students.repository.StudentRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class StudentRequestService {
    private final JwtSecretKey secretKey;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentRequestService(JwtSecretKey secretKey, StudentRepository studentRepository) {
        this.secretKey = secretKey;
        this.studentRepository = studentRepository;
    }

    public Student getStudentFromRequest(HttpServletRequest request) {
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
        return studentRepository.findByUsername(username);
    }
}
