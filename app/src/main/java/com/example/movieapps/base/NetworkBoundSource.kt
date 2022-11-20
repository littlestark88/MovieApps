package com.example.movieapps.base

import androidx.paging.PagingData
import kotlinx.coroutines.flow.*

abstract class NetworkBoundSource<ResultType, RequestType> {

    private var result: Flow<ResultType> = flow {
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emitAll(fetchFromNetwork(apiResponse.data).map {
                    ApiResponse.Success(it)
                })
            }
        }
    }

    protected abstract fun fetchFromNetwork(data: RequestType?): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<PagingData<RequestType>>

    protected open fun onFetchFailed () {}

    fun asFlow(): Flow<ResultType> = result
}