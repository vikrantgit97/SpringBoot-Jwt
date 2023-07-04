package com.spring.security.jwt.customresponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomResponse {
    private static HttpStatus httpStatus;
    private static HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    private static HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);

    public static ResponseEntity<Object> response(String code, Object data) {

        // Create the response data object
        Map<String, Object> responseData = new HashMap<String, Object>();
        responseData.put("success", true);

        // Handle error code
        String errorCode = ErrorCodes.RESPONSE_CODE.get(code);
        if (errorCode != null) {
            responseData.put("success", false);
            responseData.put("error_code", errorCode);
            Map<String, Object> mapping = new HashMap<>();
            mapping.put("error_code", ErrorCodes.RESPONSE_CODE.get(code));
            mapping.put("controller_name", handlerMethod.getBeanType().getSimpleName());
            mapping.put("method_name", handlerMethod.getMethod().getName());
            mapping.put("message", ResponseMessages.MESSAGE.get(code));
            mapping.put("HTTP_method", request.getMethod());
            mapping.put("url", request.getRequestURL().toString());
            mapping.put("data", data);
            log.error(mapping.toString());
            System.out.println(mapping.toString());
        }

        // Handle error message
        String errorMessage = ResponseMessages.MESSAGE.get(code);
        if (errorMessage != null) {
            //responseData.put("message", Collections.singletonList(errorMessage));
            responseData.put("message", errorMessage);
        }

        // Handle data
        if (data != null) {
            responseData.put("data", data);
        }

        httpStatus = ResponseStatus.STATUS_CODE.get(code);
        return new ResponseEntity<>(responseData, httpStatus);
    }
}