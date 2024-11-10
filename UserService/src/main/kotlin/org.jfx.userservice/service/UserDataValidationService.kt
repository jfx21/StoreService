package org.jfx.userservice.service

import org.jfx.userservice.model.dto.UserRegistrationDto
import org.jfx.userservice.repository.UserRepository
import org.jfx.userservice.service.validation.UserDataValidationResult
import org.springframework.stereotype.Service
import java.util.regex.Matcher
import java.util.regex.Pattern


@Service
open class UserDataValidationService(private val userRepository: UserRepository) {
    private val phoneNumberException = arrayOf(
        "PhoneNumber is taken", "Please enter correct phone number"
    )
    private val emailException = arrayOf("Email is taken", "Please enter correct email address")

    fun validateUserInputs(user: UserRegistrationDto): UserDataValidationResult {
        val result = UserDataValidationResult()
        if (!isEmailCorrect(user.email)) {
            result.validationExceptions.validationExceptions.add(emailException[1])
            result.isUserInputCorrect = false
        }
        if (!this.isPhoneNumberCorrect(user.phoneNumber)) {
            result.validationExceptions.validationExceptions.add(phoneNumberException[1])
            result.isUserInputCorrect = false
        }
        if (!this.isUsernameCorrect(user.username)) {
            result
                .validationExceptions
                .validationExceptions
                .add("Username doesn't match regex")
            result.isUserInputCorrect = false
        }
        if (!this.isPasswordCorrect(user.password)) {
            result.validationExceptions
                .validationExceptions
                .add("Password doesn't match regex")
            result.isUserInputCorrect = false
        }
        if (!this.isEmailTaken(user.email)) {
            result.validationExceptions.validationExceptions.add(emailException[0])
            result.isUserInputCorrect = false
        }
        if (!this.isPhoneNumberTaken(user.phoneNumber)) {
            result.validationExceptions.validationExceptions.add(phoneNumberException[0])
            result.isUserInputCorrect = false
        }
        return result
    }

    //for future use in changePassword function
    open fun isPasswordCorrect(password: String?): Boolean {
        val p: Pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*()-=_+])[A-Za-z\\d!@#$%^&*()-=_+]{8,}$")
        if (password == null) return false
        val m: Matcher = p.matcher(password)
        return m.matches()
    }

    private fun isEmailCorrect(email: String?): Boolean {
        val emailRegex = "[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"

        val emailPat = Pattern.compile(emailRegex)
        if (email == null) return false
        return emailPat.matcher(email).matches()
    }

    private fun isUsernameCorrect(username: String?): Boolean {
        val nameRegex = "^[a-zA-Z0-9_]{3,20}$"
        val namePat = Pattern.compile(nameRegex)
        if (username == null) return false
        return namePat.matcher(username).matches()
    }

    private fun isPhoneNumberCorrect(phoneNumber: String?): Boolean {
        val phoneNumberRegex = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])+$"
        val phonePat = Pattern.compile(phoneNumberRegex)
        if (phoneNumber == null) return false
        return phonePat.matcher(phoneNumber).matches()
    }

    private fun isEmailTaken(email: String?): Boolean {
        return userRepository.findByEmail(email!!).isEmpty
    }

    private fun isPhoneNumberTaken(phoneNumber: String?): Boolean {
        return userRepository.findByPhoneNumber(phoneNumber!!).isEmpty
    }
    //TODO add isUsernameTaken
}