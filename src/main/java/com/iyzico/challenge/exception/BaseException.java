package com.iyzico.challenge.exception;

import com.iyzico.challenge.constants.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = -8277709196836634389L;

    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }
}
