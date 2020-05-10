package com.reactivestax.spring5mvc.config;

import com.reactivestax.spring5mvc.interceptors.AccessLogsHandlerInterceptor;
import com.reactivestax.spring5mvc.interceptors.BlockHttpInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static com.reactivestax.spring5mvc.constants.CommonConstants.ENV_PROD;

/**
 * Sets up the accessLogsInterceptor and blockHttpinterceptor
 * to intercept specific paths for Non local envs
 */
@Configuration
@Profile({ENV_PROD})
public class ApplicationConfigurerNonLocalAdapter extends WebMvcConfigurationSupport {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigurerNonLocalAdapter.class);
    @Autowired
    AccessLogsHandlerInterceptor accessLogsHandlerInterceptor;

    @Autowired
    BlockHttpInterceptor blockHttpInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        logger.warn("***** http block interceptor setup *****");
        //https block interceptor should always be the first one in the list of interceptors
        registry.addInterceptor(blockHttpInterceptor)
                .addPathPatterns("/rest/**");

        registry.addInterceptor(accessLogsHandlerInterceptor)
                .addPathPatterns("/rest/**");
    }
}