package com.education.vndictionary.services.impl;

import com.education.vndictionary.common.HttpResponseUtil;
import com.education.vndictionary.common.Jwt;
import com.education.vndictionary.dtos.requests.LoginRequest;
import com.education.vndictionary.services.AuthService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final Jwt jwtUtils;

    @Override
    public UserDetailsImpl verifyByUsernamePassword(LoginRequest payload) {
        var username = payload.getUsername();
        var password = payload.getPassword();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var cookie1 = jwtUtils.generateJwtToken(username);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return userDetails;
    }

}
