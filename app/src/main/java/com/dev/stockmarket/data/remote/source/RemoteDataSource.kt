package com.dev.stockmarket.data.remote.source

import com.dev.stockmarket.data.remote.dto.CompanyInfoDto
import com.dev.stockmarket.util.dispatchers.DispatchersProvider
import com.dev.stockmarket.data.remote.service.StockApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val api: StockApi,
    private val dispatchers: DispatchersProvider
) {
    suspend fun getCompanyListing(): ApiResponse<ResponseBody> =
        suspendGetResponse {
            val response = api.getListing()

            ApiResponse.Success(response)
        }

    suspend fun getIntraDayInfo(
        symbol: String
    ): ApiResponse<ResponseBody> =
        suspendGetResponse {
            val response = api.getIntraDayInfo(symbol = symbol)

            ApiResponse.Success(response)
        }

    suspend fun getCompanyInfo(
        symbol: String
    ): ApiResponse<CompanyInfoDto> =
        suspendGetResponse {
            val response = api.getCompanyInfo(symbol = symbol)

            ApiResponse.Success(response)
        }

    private fun <T> getResponse(
        httpCall: suspend () -> ApiResponse<T>
    ): Flow<ApiResponse<T>> = flow {
        try {
            emit(httpCall())
        }
        catch (e: HttpException) {
            emit(ApiResponse.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
        catch (e: IOException) {
            emit(ApiResponse.Error(e.localizedMessage ?: "Couldn't reach server. Check your internet connection."))
        }
        catch (e: Exception) {
            emit(ApiResponse.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(dispatchers.io)

    private suspend fun <T> suspendGetResponse(
        httpCall: suspend () -> ApiResponse<T>
    ): ApiResponse<T> =
        try {
            httpCall()
        }
        catch (e: HttpException) {
            ApiResponse.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
        catch (e: IOException) {
            ApiResponse.Error(e.localizedMessage ?: "Couldn't reach server. Check your internet connection.")
        }
        catch (e: Exception) {
            ApiResponse.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
}