package com.example.monitoringproducer.config.infrastructure;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.resolvers.util.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.monitoringproducer.domain.Request.REQUEST_URI_HEADER;

@Slf4j
public class RequestArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentRequest.class) && parameter.getParameterType().equals(com.example.monitoringproducer.domain.Request.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        String remoteAddress = httpServletRequest.getRemoteAddr();
        String uri = httpServletRequest.getHeader(REQUEST_URI_HEADER);
        String query = httpServletRequest.getQueryString();
        AuthType authType = authType(httpServletRequest);
        Map<String, String> headers = headers(httpServletRequest);

        return Request.builder()
                .clientIpAddress(remoteAddress)
                .uri(uri)
                .query(query)
                .headers(headers)
                .authType(authType)
                .build();
    }

    private AuthType authType(HttpServletRequest request) {
        AuthType authType;
        try {
            String requestURI = request.getRequestURI();
            String[] parts = requestURI.split("/");
            String authTypeString = "DEFAULT";
            if (parts.length >= 2) {
                authTypeString = parts[1];
            }
            authType = AuthType.valueOf(authTypeString.toUpperCase());
        }
        catch (IllegalArgumentException exception) {
            authType = AuthType.DEFAULT;
        }
        return authType;
    }

    private Map<String, String> headers(HttpServletRequest request) {
        Enumeration<String> headerNames = Optional.ofNullable(request.getHeaderNames())
                .orElse(Collections.emptyEnumeration());

        return Collections.list(headerNames)
                .stream()
                .collect(Collectors.toMap(
                        String::toLowerCase,
                        request::getHeader
                ));

    }
}
