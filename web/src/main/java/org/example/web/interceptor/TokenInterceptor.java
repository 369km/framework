package org.example.web.interceptor;


import org.example.common.Constant;
import org.example.common.token.TokenService;
import org.example.common.token.TokenThreadLocal;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        tokenService.validateToken(request);
        tokenService.restoreToken(response, request.getHeader(Constant.TOKEN_NAME));
        MDC.put(Constant.USER_ID, String.valueOf(TokenThreadLocal.getToken().getId()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(Constant.USER_ID);
        TokenThreadLocal.remove();
    }

}

