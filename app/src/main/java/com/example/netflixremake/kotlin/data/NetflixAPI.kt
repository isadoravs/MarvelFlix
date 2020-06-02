package com.example.netflixremake.kotlin.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetflixAPI {

    private fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://tiagoaguiar.co/api/netflix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: NetflixService = retrofit().create(NetflixService::class.java)
}