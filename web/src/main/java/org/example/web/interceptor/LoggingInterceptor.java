package org.example.web.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.example.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logRequest(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    private void logRequest(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if ("GET".equalsIgnoreCase(request.getMethod()) && !StringUtils.isBlank(queryString)) {
            try {
                queryString = URLDecoder.decode(queryString, Constant.UTF8);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("convert queryString error ", e);
            }
        }
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(":").append(request.getHeader(headerName)).append("\n");
        }
        LOGGER.info("Request: [{}] [{}] [{}]\nRequest header: {}",
                request.getMethod(), request.getRequestURL(), queryString, headers.toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info("Response: [{}]", response.getStatus());
    }
}

