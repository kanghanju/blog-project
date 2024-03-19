package com.estsoft.blogjpa.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddTraceIdFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("AddTraceIdFilter init() - {}", filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("traceId", UUID.randomUUID().toString());
        log.info("2. AddTraceIdFilter before");
        chain.doFilter(request, response);
        log.info("2. AddTraceIdFilter After");
    }

    @Override
    public void destroy() {
        log.info("2. AddTraceIdFilter destroy()");
    }

}
