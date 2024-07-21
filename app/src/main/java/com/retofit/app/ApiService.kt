package com.retofit.app


import com.retofit.app.data.ExampleJson2KtKotlin
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Nikita kohli
 */
interface ApiService {
    @GET("everything")
    fun getDataList(
        @Query("q") nameString: String,
        @Query("from") dateString: String,
        @Query("sortBy") sortByString: String,
        @Query("apiKey") apiKey: String
    ): Call<ExampleJson2KtKotlin>

}