package com.weng.common.interceptor;


import com.weng.common.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@ConditionalOnClass(DispatcherServlet.class)
//DispatcherServlet是SpringMVC的核心组件，当DispatcherServlet在类路径下时，才会装载这个bean
public class UserInfoInterceptor implements HandlerInterceptor
{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
//        String attribute = (String) request.getAttribute("user-info");
//        if (StringUtils.isNotEmpty(attribute))
//        {
//            UserContext.setUser(Long.valueOf(attribute));
//        }
        String header = response.getHeader("user-info");
        if (StringUtils.isNotEmpty(header))
        {
            UserContext.setUser(Long.valueOf(header));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        UserContext.removeUser();
    }
}
