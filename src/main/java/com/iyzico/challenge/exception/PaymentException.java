package com.iyzico.challenge.exception;

import com.iyzico.challenge.constants.ErrorCode;

public class PaymentException extends BaseException {

    private static final long serialVersionUID = 1678262113742045119L;

    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
