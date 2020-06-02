package com.example.netflixremake.kotlin.data.response

data class Response<T>(
        val code: Int,
        val etag: String,
        val data: Data<T>
)