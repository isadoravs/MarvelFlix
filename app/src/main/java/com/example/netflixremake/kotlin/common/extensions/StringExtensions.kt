package com.example.netflixremake.kotlin.common.extensions

import java.security.MessageDigest

fun String.toMD5() =
        MessageDigest
                .getInstance("MD5")
                .digest(toByteArray())
                .toHex()

fun ByteArray.toHex() = joinToString("") {
    "%02x".format(it)
}