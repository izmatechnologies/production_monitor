package com.faisal.quc

import com.faisal.quc.core.data.DataResource
import com.faisal.quc.core.data.ErrorType
import com.faisal.quc.core.extention.errorMessage
import com.faisal.quc.core.extention.log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

class ApiDispatcherCaller {
    companion object {
        suspend fun <T> safeApiCall(
            dispatcher: CoroutineDispatcher,
            apiCall: suspend () -> T
        ): DataResource<*> {
            return withContext(dispatcher) {
                try {
                    val response = apiCall.invoke()
                    response as Response<*>
                    if (response.isSuccessful) {
                        DataResource.success(
                            data = response.body(),
                            code = response.code()
                        )
                    } else {
                        response.errorBody().toString().log("API")
                        response.errorBody().errorMessage?.let {
                            DataResource.error(
                                errorType = ErrorType.API,
                                code = response.code(),
                                message = it.message,
                                data = it
                            )
                        } ?: kotlin.run {
                            DataResource.error(
                                errorType = ErrorType.UNKNOWN,
                                code = response.code(),
                                message = null
                            )
                        }
                    }
                } catch (throwable: Throwable) {
                    throwable.toString().log("API")
                    when (throwable) {
                        is IOException -> {
                            DataResource.error(
                                errorType = ErrorType.IO,
                                code = null,
                                message = throwable.localizedMessage
                            )
                        }
                        is HttpException -> {
                            DataResource.error(
                                errorType = ErrorType.NETWORK,
                                code = throwable.code(),
                                message = throwable.localizedMessage
                            )
                        }
                        else -> {
                            DataResource.error(
                                errorType = ErrorType.UNKNOWN,
                                code = null,
                                message = null
                            )
                        }
                    }
                }
            }
        }
    }
}