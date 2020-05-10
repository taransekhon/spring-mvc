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

import static com.reactivestax.spring5mvc.constants.CommonConstants.*;


/**
 * Sets up the accessLogsInterceptor to intercept specific paths
 *
 */
@Configuration
@Profile({ENV_DEFAULT, ENV_LOCAL, ENV_IST, ENV_SDF})
public class ApplicationConfigurerLocalAdapter extends WebMvcConfigurationSupport {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigurerLocalAdapter.class);
    @Autowired
    AccessLogsHandlerInterceptor accessLogsHandlerInterceptor;
    @Autowired
    BlockHttpInterceptor blockHttpInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        logger.warn("***** http block interceptor NOT setup *****");

//        registry.addInterceptor(blockHttpInterceptor)
//                .addPathPatterns("/rest/**");
        registry.addInterceptor(accessLogsHandlerInterceptor)
                .addPathPatterns("/rest/**");
    }
}