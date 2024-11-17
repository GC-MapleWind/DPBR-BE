package com.dpbr.dpbrbe.global.auth.exception;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.error.exception.ServiceException;

public class InvalidEmailException extends ServiceException {
	public InvalidEmailException() {
		super(ErrorCode.INVALID_EMAIL);
	}
}
