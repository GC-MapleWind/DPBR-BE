package com.dpbr.dpbrbe.domain.user.exception;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.error.exception.ServiceException;

public class UserNotFoundException extends ServiceException {
	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}
}
