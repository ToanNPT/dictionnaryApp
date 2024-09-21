package com.education.vndictionary.controllers;

import com.education.vndictionary.common.HttpResponseUtil;
import com.education.vndictionary.common.Jwt;
import com.education.vndictionary.dtos.BaseHttpResponse;
import com.education.vndictionary.dtos.requests.LoginRequest;
import com.education.vndictionary.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final Jwt jwtUtils;

    @PostMapping("/auth/login")
    public BaseHttpResponse doLogin(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        // Do login
        var userInfo =  this.authService.verifyByUsernamePassword(loginRequest);
        if(userInfo == null){
            return HttpResponseUtil.createErrorResponse("Login failed", HttpStatus.UNAUTHORIZED);
        }else {
            var cookie1 = jwtUtils.generateJwtToken(loginRequest.getUsername());
            response.addCookie(cookie1);
            return HttpResponseUtil.createSuccessResponse(userInfo);
        }

    }
}
