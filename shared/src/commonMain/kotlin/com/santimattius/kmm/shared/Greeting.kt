package com.santimattius.kmm.shared


class Greeting {
    fun greeting(): String {
//        return "Hello, ${Platform().platform}!"
        return "Guess what it is! > ${Platform().platform.reversed()}!"
    }
}