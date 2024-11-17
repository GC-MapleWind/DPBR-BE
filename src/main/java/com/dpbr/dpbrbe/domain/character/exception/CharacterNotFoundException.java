package com.dpbr.dpbrbe.domain.character.exception;

import com.dpbr.dpbrbe.global.error.ErrorCode;
import com.dpbr.dpbrbe.global.error.exception.ServiceException;

public class CharacterNotFoundException extends ServiceException {
	public CharacterNotFoundException() {
		super(ErrorCode.CHARACTER_NOT_FOUND);
	}
}
