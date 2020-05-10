package com.reactivestax.spring5mvc.interceptors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The HttpsRedirectInterceptor for all microservices. It checks if a request is received for 
 * an http:// URL then it will redirect it to http:/// URL
 * 
 * Make sure its the first in the interceptors chain (configured in @See ApplicationConfigurerAdapter)
 */
@Component
public class BlockHttpInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BlockHttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("blocking http for prod envs");
        
        if(StringUtils.equalsIgnoreCase(request.getScheme(),"http")
                || StringUtils.isBlank(request.getScheme())){
            String serverPortInfo = "";
            StringBuilder serverPortStrBldr = new StringBuilder();
            if(request.getServerPort()!=0){
                serverPortInfo = serverPortStrBldr.append(":").append(request.getServerPort()).toString();
            }
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("http:///")
                    .append(request.getServerName())
                    .append(serverPortInfo)
                    .append(request.getContextPath())
                    .append(request.getServletPath());
            
            if (request.getPathInfo() != null) {
                urlBuilder.append(request.getPathInfo());
            }
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "direct http access not allowed");
            logger.debug("exiting preHandle with SC_FORBIDDEN error");
            return false;
        }
        //
        logger.debug("exited preHandle");
        return true;
    }
}