package com.pagination.app.network.retrofit

import androidx.annotation.MainThread
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

abstract class NetworkOnlineDataRepo<RESULT, REQUEST> {
    fun asFlow() = flow {
        emit(DataResult.Loading())
        try {
            val apiResponse = fetchDataFromRemoteSource()
            val data = apiResponse.body()

            if (apiResponse.isSuccessful && data != null) {
                emit(DataResult.Success(data))
            } else {
                emit(
                    DataResult.Failure(
                        getErrorMsg(apiResponse.errorBody()!!),
                        errorCode = apiResponse.code()
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                DataResult.Failure(
                    e.message, e
                )
            )
        }

    }

    private fun getErrorMsg(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.message!!
        }

    }

    @MainThread
    protected abstract suspend fun fetchDataFromRemoteSource(): Response<REQUEST>
}