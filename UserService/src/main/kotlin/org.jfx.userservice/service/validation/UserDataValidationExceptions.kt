package org.jfx.userservice.service.validation

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor
import lombok.Setter

@NoArgsConstructor
@Setter
class UserDataValidationExceptions(@JsonProperty val validationExceptions: MutableList<String> = ArrayList()) {
}