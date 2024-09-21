package com.education.vndictionary.security.filters;

import com.education.vndictionary.configs.CustomContext;
import com.education.vndictionary.configs.CustomContextHolder;
import com.education.vndictionary.services.impl.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ThreadContextFilter extends OncePerRequestFilter {

    private CustomContextHolder holder;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null){
            var ctxHolder = new CustomContextHolder();
            var ctx = new CustomContext();
            ctx.setUserDetails((UserDetailsImpl) principal);
            CustomContextHolder.setContext(ctx);
        }

    }
}
