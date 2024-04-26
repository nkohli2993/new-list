package com.pagination.app.network.retrofit

import com.pagination.app.ApiConstants
import com.pagination.app.data.APIDataBean
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Nikita kohli
 */
interface ApiService {

    @GET(ApiConstants.LIST_SHOW)
    suspend fun getDataList(): Response<ArrayList<APIDataBean>>
}