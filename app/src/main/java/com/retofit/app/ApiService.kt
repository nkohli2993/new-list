package com.retofit.app

import com.retofit.app.ApiConstants
import com.retofit.app.data.APIDataBean
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Nikita kohli
 */
interface ApiService {
    @GET(ApiConstants.LIST_SHOW)
    fun getDataList(): Call<ArrayList<APIDataBean>>

}