package com.reactivestax.spring5mvc.interceptors;

import com.google.gson.Gson;
import com.reactivestax.spring5mvc.constants.CommonConstants;
import com.reactivestax.spring5mvc.exceptions.InvalidDataException;
import com.reactivestax.spring5mvc.utils.dto.ClientMetaData;
import com.reactivestax.spring5mvc.utils.dto.RequestedResource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * The accessLogsInterceptor for all microservices. It retrieves the client-metadata
 * from the requestHeaders and persists it in logs. In near future the support for persisting the logs
 * into a persistent store will be turned on as well. 
 */
@Component
public class AccessLogsHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AccessLogsHandlerInterceptor.class);
    private static final String REQUEST_URL_STR_CONSTANT= "Request URL::";
    //
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("entered preHandle");
        if (StringUtils.isNotBlank(request.getHeader(CommonConstants.CLIENT_METADATA_REQ_HEADER))) {
            String clientMetaDataJsonStr = request.getHeader(CommonConstants.CLIENT_METADATA_REQ_HEADER);
            processClientMetaData(clientMetaDataJsonStr, request);
        } else {
            InvalidDataException invalidDataException = new InvalidDataException();
            invalidDataException.getErrorMap().put(CommonConstants.CLIENT_METADATA_REQ_HEADER,
                "client_metadata request header cannot be missing or have blank value");
            throw invalidDataException;
        }
        // log the basic perf metrics
        logEntryTimings(request);
        //
        logger.debug("exited preHandle");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("entered afterCompletion");
        // basic perf metrics logs
        logExitTimings(request);
        //
        logger.debug("exited afterCompletion");
    }

    private void processClientMetaData(String clientMetaDataHeader, HttpServletRequest request) {
        deserializeClientMetadata(clientMetaDataHeader, request);
        logClientMetaData(request);
    }

    private void deserializeClientMetadata(String clientMetaDataJsonStr, HttpServletRequest request) {
        com.google.gson.Gson gson = new Gson();
        
        RequestedResource rqResource = new RequestedResource();
        rqResource.setRequestUri(request.getRequestURI());        
        String requestUrl = StringUtils.isNotBlank(request.getRequestURL()) ? request.getRequestURL().toString() : "";
        rqResource.setRequestUrl(requestUrl);
        request.setAttribute(CommonConstants.REQUESTED_RESOURCE, rqResource);
        
        //
        ClientMetaData clientMetaData = new ClientMetaData();
        try {
            clientMetaData = gson.fromJson(clientMetaDataJsonStr, ClientMetaData.class);
        } catch (Exception e) {
        	logger.error("{}",e.getMessage());
            InvalidDataException invalidDataException = new InvalidDataException(e);
            invalidDataException.getErrorMap().put(CommonConstants.CLIENT_METADATA_REQ_HEADER,
                "Error while parsing the client_metadata_json_str=" + clientMetaDataJsonStr);
            throw invalidDataException;
        }
        validateClientMetadata(clientMetaData);
        request.setAttribute(CommonConstants.CLIENT_METADATA, clientMetaData);
    }

    private void validateClientMetadata(ClientMetaData clientMetaData) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ClientMetaData>> constraintViolations = validator.validate(clientMetaData);
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            InvalidDataException invalidDataException = new InvalidDataException();
            for (ConstraintViolation<ClientMetaData> constraintViolation : constraintViolations) {
                String propertyPath = constraintViolation.getPropertyPath().toString();
                String message = constraintViolation.getMessage();
                invalidDataException.getErrorMap().put(propertyPath, message);
            }
            throw invalidDataException;
        }
    }

    private void logClientMetaData(HttpServletRequest request) {
        ClientMetaData clientMetaData = (ClientMetaData) request.getAttribute(CommonConstants.CLIENT_METADATA);
        logger.debug("logging clientMetaData: {}" , clientMetaData);
    }

    private void logExitTimings(HttpServletRequest request) {
        long startTime = (Long) request.getAttribute("startTime");
        logger.info("{} {} {} {}",REQUEST_URL_STR_CONSTANT , request.getRequestURL().toString() , ":: End Time=" , System.currentTimeMillis());
        logger.info("{} {} {} {}",REQUEST_URL_STR_CONSTANT , request.getRequestURL().toString() , ":: Time Taken=" , (System.currentTimeMillis() - startTime));
    }

    private void logEntryTimings(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("{} {} {} {}",REQUEST_URL_STR_CONSTANT , request.getRequestURL().toString() , ":: Start Time=" , System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
    }
}