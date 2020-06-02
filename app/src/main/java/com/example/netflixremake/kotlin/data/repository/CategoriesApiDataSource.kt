package com.example.netflixremake.kotlin.data.repository

import com.example.netflixremake.kotlin.data.CategoriesResult
import com.example.netflixremake.kotlin.data.NetflixAPI
import com.example.netflixremake.kotlin.data.response.CategoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesApiDataSource : CategoriesRepository {
    override fun getCategories(categoriesResultCallback: (result: CategoriesResult) -> Unit) {
        NetflixAPI.service
                .listCategories()
                .enqueue(object : Callback<CategoriesResponse> {
                    override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                        categoriesResultCallback(CategoriesResult.ServerError)
                    }

                    override fun onResponse(call: Call<CategoriesResponse>, response: Response<CategoriesResponse>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                categoriesResultCallback(CategoriesResult.Success(it.categories))
                            }
                        }
                    }

                })
    }
}

