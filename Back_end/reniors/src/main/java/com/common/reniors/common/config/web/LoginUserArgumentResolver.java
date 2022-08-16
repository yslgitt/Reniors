package com.common.reniors.common.config.web;

import com.common.reniors.common.exception.NotMatchException;
import com.common.reniors.domain.entity.Company;
import com.common.reniors.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        System.out.println("LoginUserArgumentResolver - supportsParameter");
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isLongClass = User.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isLongClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("LoginUserArgumentResolver - resolveArgument");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
            if(authentication.getPrincipal()=="anonymousUser"){
                return (Company) authentication.getPrincipal();
            }
            return (User) authentication.getPrincipal();
        } catch (ClassCastException e) {
            throw new NotMatchException("토큰 정보가 잘못되었습니다.");
        }
    }
}