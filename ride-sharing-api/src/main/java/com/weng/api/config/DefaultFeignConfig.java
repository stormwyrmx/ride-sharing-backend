package com.weng.api.config;

import com.weng.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }

    /**
     * 给请求添加一个自定义的请求头信息，该请求头的名称为"user-info"，其值为当前用户的ID。
     */
    @Bean
    public RequestInterceptor userInfoRequestInterceptor()
    {
        return new RequestInterceptor()
        {
            @Override
            public void apply(RequestTemplate requestTemplate)
            {
                Long userId = UserContext.getUser();
                if(userId == null) {
                    // 如果为空则直接跳过
                    return;
                }
                requestTemplate.header("user-info", String.valueOf(userId));
            }
        };
    }
//    @Bean
//    public ItemClientFallbackFactory itemClientFallbackFactory(){
//        return new ItemClientFallbackFactory();
//    }
}