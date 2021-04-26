package com.zuul.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenVerifier.class);

    @Autowired
    public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        Cookie[] cookies = request.getCookies();
        String url = request.getRequestURI();
        String[] allowedPaths = {"/registration", "/login", "/manage/country",
                "/manage/language", "/manage/major", "/manage/topic",
                "/posting/post", "/comment", "/manage/student", "/image"};

        boolean isAllowedPath = false;

        for(String path: allowedPaths) {
            if(url.contains(path)) {
                isAllowedPath = true;
                break;
            }
        }

        if(!isAllowedPath) {
            try {
                for(Cookie cookie : cookies) {
                    if(cookie.getName().equals("jwt")) {
                        token = cookie.getValue();
                    }
                }
            } catch (Exception e) {
                logger.error("No cookies");
                logger.error(url);
                e.printStackTrace();
            }

            try {

                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token);

                Claims body = claimsJws.getBody();
                String username = body.getSubject();

                // Extracting authorities which is a list of key value pairs
                List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

                // Authentication expects set of authorities, so we're going to manipulate data a bit
                Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(item -> new SimpleGrantedAuthority(item.get("authority")))
                        .collect(Collectors.toSet());


                // From this point on user can be authenticated
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        grantedAuthorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException exception) {
                throw new IllegalStateException(String.format("Token: %s cannot be trusted", token));
            }
        }
        filterChain.doFilter(request, response);
    }
}
