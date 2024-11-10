package org.jfx.userservice.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JsonUtil {
    private val logger: Logger = LoggerFactory.getLogger(JsonUtil::class.java)

    fun toJson(value: Any?): String? {
        var json: String? = null
        try {
            json = ObjectMapper().writeValueAsString(value)
        } catch (ex: JsonProcessingException) {
            logger.error("Error: ", ex)
        }
        return json
    }
}