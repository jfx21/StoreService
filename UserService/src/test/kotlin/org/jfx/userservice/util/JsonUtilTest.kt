package org.jfx.userservice.util

import org.jfx.userservice.model.Role
import org.jfx.userservice.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class JsonUtilTest{
    private val jsonUtil = JsonUtil()
    private val userJSON = "{\"password\":\"password123\",\"roles\":[\"USER\"],\"enabled\":true,\"credentialsNonExpired\":true,\"authorities\":[{\"authority\":\"USER\"}],\"accountNonExpired\":true,\"accountNonLocked\":true,\"username\":\"User200\"}"
    @Test
    fun `create json object with jsonUtil and except they are equal`(){
       assertEquals(userJSON,jsonUtil.toJson(User( "User200", "password123", "email@email.com","200200200" ,setOf(Role.USER))))
        //funny feature sometimes UserDetails attributes "switch" places, so it will fail
    }
}
