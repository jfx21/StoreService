package org.jfx.userservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


//@ResponseStatus(HttpStatus.BAD_REQUEST)
class UserDataIsNotValidException(validationResult: String?) : RuntimeException(validationResult)