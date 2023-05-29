package com.iyzico.challenge.exception;

import com.iyzico.challenge.constants.ErrorCode;

public class FlightException extends BaseException {

    private static final long serialVersionUID = -8277709196836634389L;

    public FlightException(ErrorCode errorCode) {
        super(errorCode);
    }
}
