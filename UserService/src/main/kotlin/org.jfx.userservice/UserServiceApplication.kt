package org.jfx.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class UserServiceApplication
fun main(args: Array<String>){
    runApplication<UserServiceApplication>(*args)
}