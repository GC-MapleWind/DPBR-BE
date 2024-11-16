package com.dpbr.dpbrbe.global.jwt.exception;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.error.exception.ServiceException;

public class UnsupportedTokenException extends ServiceException {
    public UnsupportedTokenException() {
        super(ErrorCode.UNSUPPORTED_TOKEN);
    }
}
