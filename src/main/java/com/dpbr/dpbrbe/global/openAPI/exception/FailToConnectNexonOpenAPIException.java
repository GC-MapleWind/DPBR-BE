package com.dpbr.dpbrbe.global.openAPI.exception;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.error.exception.ServiceException;

public class FailToConnectNexonOpenAPIException extends ServiceException {
    public FailToConnectNexonOpenAPIException() {
        super(ErrorCode.FAIL_TO_CONNECT_NEXON_OPEN_API);
    }
}
