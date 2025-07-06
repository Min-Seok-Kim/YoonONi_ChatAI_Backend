package com.example.YoonONI_BackEnd.config;

import com.example.YoonONI_BackEnd.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.net.http.HttpRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(RequestDataSet.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();



        RequestDataSet dataSet = new RequestDataSet();

        dataSet.setUserService(userDetailsService);

        dataSet.arrangeInputParameters(request);

        if (log.isInfoEnabled() || log.isDebugEnabled()) {
            log.info("#### {} ####", dataSet.printInputParams());
            log.debug("#### {} ####", dataSet.printInputParams());
        }

        return dataSet;
    }
}
