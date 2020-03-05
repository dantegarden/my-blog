package com.dg.myblog.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @description: 资源找不到异常
 * @author: lij
 * @create: 2020-03-01 20:57
 */
@ResponseStatus(HttpStatus.NOT_FOUND) //404
public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
