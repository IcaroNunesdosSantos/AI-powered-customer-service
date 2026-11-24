package com.empresa.atendimento.shared.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String correlationId = UUID.randomUUID().toString();
        request.setAttribute("correlationId", correlationId);

        long startTime = System.currentTimeMillis();
        log.info("[{}] {} {} - Started", correlationId, request.getMethod(), request.getRequestURI());

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;
        log.info("[{}] {} {} - Completed in {}ms with status {}",
            correlationId, request.getMethod(), request.getRequestURI(), duration, response.getStatus());
    }
}
