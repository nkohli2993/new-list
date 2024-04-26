package com.pagination.app.repository

import com.pagination.app.data.APIDataBean
import com.pagination.app.network.retrofit.ApiService
import com.pagination.app.network.retrofit.DataResult
import com.pagination.app.network.retrofit.NetworkOnlineDataRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PaginationRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getDataList(): Flow<DataResult<ArrayList<APIDataBean>>> {
        return object : NetworkOnlineDataRepo<ArrayList<APIDataBean>, ArrayList<APIDataBean>>() {
            override suspend fun fetchDataFromRemoteSource(): Response<ArrayList<APIDataBean>> {
                return apiService.getDataList()
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }
}