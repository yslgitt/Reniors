package com.common.jmark.common.config.security.filter;

import com.common.jmark.common.config.security.service.AuthService;
import com.common.jmark.common.config.security.service.LoginCompanyDetails;
import com.common.jmark.common.config.security.service.LoginUserDetails;
import com.common.jmark.common.config.security.util.JwtUtil;
import com.common.jmark.domain.entity.Company;
import com.common.jmark.domain.entity.user.User;
import com.common.jmark.domain.repository.CompanyRepository;
import com.common.jmark.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.StringTokenizer;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    private final String LoginUserDetailsClassName = "com.common.jmark.common.config.security.service.LoginUserDetails";
    private final String LoginCompanyDetailsClassName = "com.common.jmark.common.config.security.service.LoginCompanyDetails";



    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("JwtAuthenticationFilter - doFilter");
        String jwtToken = extractToken((HttpServletRequest) request);

        if (StringUtils.hasText(jwtToken) && jwtUtil.isValidToken(jwtToken)) {
            UserDetails userDetails = authService.loadUserByUsername(jwtUtil.getSubject(jwtToken));

            StringTokenizer st = new StringTokenizer(jwtUtil.getSubject(jwtToken),",");
            String id = st.nextToken();
            String name = st.nextToken();

            if (name.equals("user")) {
                LoginUserDetails loginUserDetails = (LoginUserDetails) userDetails;

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(loginUserDetails.getUser(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                LoginCompanyDetails loginCompanyDetails = (LoginCompanyDetails) userDetails;

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(loginCompanyDetails.getCompany(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        System.out.println("JwtAuthenticationFilter - extractToken");
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
