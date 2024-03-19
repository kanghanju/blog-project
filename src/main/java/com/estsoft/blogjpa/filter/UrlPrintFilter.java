package com.estsoft.blogjpa.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlPrintFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("1. url print filter init() - {}",filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("1. UrlPrintFilter {}",httpServletRequest.getRequestURL().toString());

        log.info("1. UrlPrintFilter before");
        chain.doFilter(request,response);
        log.info("1. UrlPrintFilter After");
    }

    @Override
    public void destroy() {
        log.info("1. url print filter destroy");
    }
}
