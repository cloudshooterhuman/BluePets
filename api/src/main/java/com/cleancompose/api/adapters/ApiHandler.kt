/*
 * Copyright 2024 Abdellah Selassi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cleancompose.api.adapters

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.cleancompose.domain.models.NetworkError
import com.cleancompose.domain.models.NetworkException
import com.cleancompose.domain.models.NetworkResult
import com.cleancompose.domain.models.NetworkSuccess
import retrofit2.Response

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun <T : Any> handleApi(
    execute: () -> Response<T>,
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        // 2XX
        if (response.isSuccessful && body != null) {
            Log.e("myapp", "onResponse isSuccessful ${response.raw() ?: ""}")
            NetworkSuccess(body)
        } else {
            // Http Errors 4XX, 5XX ...
            // HttpException for any non-2xx HTTP status codes.
            Log.e("myapp", "onResponse isSuccessful ${response.raw() ?: ""}")
            NetworkError(code = response.code(), message = response.errorBody()?.string())
        }
    } catch (e: Throwable) {
        // IOException (is a Throwable) for network failures.
        // network is broken, ect ..
        NetworkException(e)
    }
}
