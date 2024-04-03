package com.faisal.quc.core.extention

import androidx.lifecycle.MutableLiveData
import com.faisal.quc.ApiDispatcherCaller
import com.faisal.quc.core.data.DataResource
import com.faisal.quc.core.data.ErrorResponse
import com.faisal.quc.core.data.ErrorType

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

/**
 * Generic method to call api within Coroutine Scope
 */
fun <T> CoroutineScope.callApi(
    dispatcher: CoroutineDispatcher,
    liveData: MutableLiveData<DataResource<*>>,
    api: suspend () -> T
) {
    liveData.postValue(DataResource.loading(null))
    launch(dispatcher) {
        liveData.postValue(ApiDispatcherCaller.safeApiCall(dispatcher, api))
    }
}

/**
 * Generic method to fetch data from repository
 */
fun CoroutineScope.callRepo(
    dispatcher: CoroutineDispatcher,
    liveData: MutableLiveData<DataResource<*>>,
    repoMethod: suspend () -> DataResource<*>?
) {
    liveData.postValue(DataResource.loading(null))
    launch(dispatcher) {
        liveData.postValue(repoMethod.invoke())
    }
}

/**
 * Getting exact Error Body Response from the API
 */
val ResponseBody?.errorMessage: ErrorResponse?
    get() {
        try {
            return Gson().fromJson<Any>(
                this!!.string(),
                ErrorResponse::class.java
            ) as? ErrorResponse
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher,
                        apiCall: suspend () -> T): DataResource<*> {
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


suspend fun <T> safeDataBaseCall(dispatcher: CoroutineDispatcher,
                            daoCall: suspend () -> T): DataResource<*> {
    return withContext(dispatcher) {
        try {
            val response = daoCall.invoke()

            DataResource.success(
                data = response,
                code = 200
            )

        } catch (throwable: Throwable) {
            throwable.toString().log("db")
            DataResource.error(
                errorType = ErrorType.IO,
                code = null,
                message = throwable.localizedMessage
            )

        }
    }
}


