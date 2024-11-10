package org.jfx.userservice.service.validation

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.NoArgsConstructor

@NoArgsConstructor
open class UserDataValidationResult(
    @JsonProperty
    var isUserInputCorrect: Boolean = true,
    @JsonProperty var validationExceptions: UserDataValidationExceptions = UserDataValidationExceptions()
) {
}