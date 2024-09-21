package com.education.vndictionary.exceptions;

import com.education.vndictionary.common.Messages;
import com.education.vndictionary.dtos.BaseHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler({AppErrorException.class})
    @ResponseBody
    public BaseHttpResponse handlingCustomException(AppErrorException e) {
        Integer status = e.getStatus();
        String errMess = e.getMessage();
        BaseHttpResponse response = new BaseHttpResponse();
        response.setStatus(status);
        response.setMessage(errMess);
        response.setData(null);

        return response;
    }


}
