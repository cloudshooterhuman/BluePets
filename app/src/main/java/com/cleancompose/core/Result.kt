package com.cleancompose.core

import android.util.Log
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L

@Immutable
sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Log.v("myapp", "Success" + it.toString())
            Result.Success(it)
        }
        .onStart {
            Log.v("myapp", "Result.Loading")
            emit(Result.Loading)
        }
        .retryWhen { cause, _ ->
            if (cause is IOException) {
                Log.v("myapp", cause.message!!)
                emit(Result.Error(cause))

                delay(RETRY_TIME_IN_MILLIS)
                true
            } else {
                Log.v("myapp", "retryWhen")
                false
            }
        }
        .catch {
            Log.v("myapp", it.message.toString())
            emit(Result.Error(it))
        }
}