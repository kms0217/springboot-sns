package com.kms.mygram.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("local")
public class RequestInterceptor implements AsyncHandlerInterceptor {

    private final HibernateInterceptor hibernateInterceptor;

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        hibernateInterceptor.clearCount();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        hibernateInterceptor.startCount();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long count = hibernateInterceptor.getCount();
        hibernateInterceptor.clearCount();
        log.info("request : {} {} , QueryCount : {}", request.getMethod(), request.getRequestURI(), count);
    }
}
