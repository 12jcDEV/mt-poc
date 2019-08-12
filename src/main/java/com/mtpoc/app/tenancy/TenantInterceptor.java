package com.mtpoc.app.tenancy;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mtpoc.app.tenancy.MultiTenantConstants.DEFAULT_TENANT_ID;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

    //@Value("${jwt.header}")
    private String tokenHeader;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Prehandle");

        String authToken = request.getHeader("Authorization");
        String tid = request.getHeader("tid");
        System.out.println("TID: " + tid);
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);

        if(StringUtils.isEmpty(authToken))
            tid = DEFAULT_TENANT_ID;


        TenantContext.setCurrentTenant(tid);

        return true;
    }

    @Override
    public void postHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {
        System.out.println();
        TenantContext.clear();
    }
}
