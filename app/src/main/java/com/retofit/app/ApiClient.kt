package com.retofit.app

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Dumadu on 26-Oct-17.
 */
//class ApiClient {
//    var BASE_URL: String = ApiConstants.BASE_URL
//    var retrofit: Retrofit? = null
//
//    fun getApiClient(): Retrofit? {
//        if (retrofit == null) {
//            retrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create()).build()
//        }
//        return retrofit
//    }
//}

class ApiClient {
    var BASE_URL: String = ApiConstants.BASE_URL
    var retrofit: Retrofit? = null

    fun getApiClient(): Retrofit? {
        if (retrofit == null) {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }
}