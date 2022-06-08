package com.example.monitoringproducer.config.infrastructure;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ImmediateResponseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!response.isCommitted()) {
            sendResponse(response);
        }
        return true;
    }

    private void sendResponse(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        writer.close();
        writer.flush();
    }
}
