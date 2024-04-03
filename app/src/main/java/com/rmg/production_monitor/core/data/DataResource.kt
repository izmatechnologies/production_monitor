package com.faisal.quc.core.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.*

/**
 * A generic class that describes data with a status
 */
data class DataResource<out T> constructor(
    val status: ApiCallingStatus,
    val data: T? = null,
    val errorType: ErrorType? = null,
    val code: Int = 0,
    val message: String? = null,
    val date: Date? = null
) {
    companion object {
        fun <T> success(
            data: T?,
            code: Int = 0,
            date: Date? = null
        ): DataResource<T> {
            return DataResource(
                status = ApiCallingStatus.SUCCESS,
                data = data,
                code = code,
                date = date
            )
        }

        fun <T> error(
            errorType: ErrorType?,
            code: Int?,
            message: String?,
            data: T? = null,
            date: Date? = null
        ): DataResource<T> {
            return DataResource(
                status = ApiCallingStatus.ERROR,
                errorType = errorType,
                code = code ?: 0,
                message = message,
                data = data,
                date = date
            )
        }

        fun <T> loading(data: T?): DataResource<T> {
            return DataResource(
                status = ApiCallingStatus.LOADING,
                data = data
            )
        }
    }
}

typealias DataBundle = DataResource<*>
typealias DataBundleLiveData = MutableLiveData<DataResource<*>>
typealias DataObserver = Observer<DataResource<*>>
//typealias DataPagingObserver = PagingData<DataResource<*>>

enum class ApiCallingStatus {
    SUCCESS, ERROR, LOADING
}

enum class ErrorType {
    NETWORK, IO, UNKNOWN, API
}