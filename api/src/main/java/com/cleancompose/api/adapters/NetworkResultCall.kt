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
import com.cleancompose.domain.models.NetworkException
import com.cleancompose.domain.models.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>,
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(object : Callback<T> {
            @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callScope.launch {
                    val handleApi = handleApi { response }
                    callback.onResponse(this@NetworkResultCall, Response.success(handleApi))
                }
            }

            // why returning Response.success here ?
            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e("myapp", "onFailure $t")
                callback.onResponse(
                    this@NetworkResultCall,
                    Response.success(NetworkException(t)),
                )
            }
        })
    }

    override fun execute(): Response<NetworkResult<T>> = throw NotImplementedError()
    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone())
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() { proxy.cancel() }

    companion object {
        var callScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}
