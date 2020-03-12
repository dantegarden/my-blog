package com.dg.myblog.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @description: 访问过于频繁
 * @author: lij
 * @create: 2020-03-01 20:57
 */
@ResponseStatus(HttpStatus.CONFLICT) //409
public class RateLimitException extends RuntimeException{

    public RateLimitException() {
    }

    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(String message, Throwable cause) {
        super(message, cause);
    }

}
