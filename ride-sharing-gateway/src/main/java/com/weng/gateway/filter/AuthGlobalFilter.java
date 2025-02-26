package com.weng.gateway.filter;

import com.weng.common.utils.JwtUtil;
import com.weng.gateway.config.AuthProperties;
import com.weng.common.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component //将过滤器交给spring容器管理
//@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtUtil jwtUtil;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //登录校验
        //1.获取请求头
        ServerHttpRequest request = exchange.getRequest();
        //2.判断请求路径是否在excludePaths中
        List<String> excludePaths = authProperties.getExcludePaths();
        for (String excludePath : excludePaths) {
            if (antPathMatcher.match(excludePath, request.getPath().toString())) {
                //如果在excludePaths中，则直接放行
                return chain.filter(exchange);
            }
        }
        //3.获取token
        String token = getJwtFromRequest(request);
//        List<String> headers = request.getHeaders().get("authorization");
//        String token = null;
//        if (!CollectionUtils.isEmpty(headers)) {
//            token = headers.get(0);
//        }
        Claims claims;
        //4.校验jwt，得到userId
        try {
            claims = jwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
        } catch (Exception e) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //5.传递用户信息
        ServerWebExchange serverWebExchange = exchange.mutate()
                .request(builder -> builder.header("user-info", claims.get("id").toString()))
                .build();
        //6.返回，放行
        return chain.filter(serverWebExchange);
    }

    @Override
    public int getOrder() {
        //返回值越小，优先级越高，保证在NettyRoutingFilter之前执行
        return 0;
    }

    private String getJwtFromRequest(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get("Authorization");
        String token=null;
        if (!CollectionUtils.isEmpty(headers)) {
            token = headers.get(0);
        }
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

}
