package com.weng.gateway.filter;

import com.weng.common.properties.JwtProperties;
import com.weng.common.utils.JwtUtil;
import com.weng.gateway.config.AuthProperties;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component //将过滤器交给spring容器管理
//public class MyFilter implements Filter, Ordered {
public class MyFilter extends OncePerRequestFilter implements Ordered {
    @Resource
    private AuthProperties authProperties;
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private JwtUtil jwtUtil;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public int getOrder() {
        //返回值越小，优先级越高，保证在NettyRoutingFilter之前执行
        return 0;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断请求路径是否在excludePaths中
        String path = request.getRequestURI();
        List<String> excludePaths = authProperties.getExcludePaths();
        for (String excludePath : excludePaths) {
            if (antPathMatcher.match(excludePath, path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        // 获取token
        String token = request.getHeader("authorization");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            // 校验jwt，得到userId
            Claims claims = jwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            // 传递用户信息
//            request.setAttribute("user-info", claims.get("id").toString());
            response.setHeader("user-info", claims.get("id").toString());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        // 判断请求路径是否在excludePaths中
//        String path = request.getRequestURI();
//        List<String> excludePaths = authProperties.getExcludePaths();
//        for (String excludePath : excludePaths) {
//            if (antPathMatcher.match(excludePath, path)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//        }
//        // 获取token
//        String token = request.getHeader("authorization");
//        if (token == null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//        try {
//            // 校验jwt，得到userId
//            Claims claims = jwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
//            // 传递用户信息
//            request.setAttribute("user-info", claims.get("id").toString());
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//    }
}
