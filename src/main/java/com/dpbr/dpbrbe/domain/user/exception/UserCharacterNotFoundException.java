package com.dpbr.dpbrbe.domain.user.exception;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.error.exception.ServiceException;

public class UserCharacterNotFoundException extends ServiceException {
	public UserCharacterNotFoundException() {
		super(ErrorCode.USER_CHARACTER_NOT_FOUND);
	}
}
