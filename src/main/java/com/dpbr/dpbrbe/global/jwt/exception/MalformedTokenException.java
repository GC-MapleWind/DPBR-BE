package com.dpbr.dpbrbe.global.jwt.exception;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.error.exception.ServiceException;

public class MalformedTokenException extends ServiceException {
    public MalformedTokenException() {
        super(ErrorCode.MALFORMED_TOKEN);
    }
}
