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
package com.cleancompose.ui.presentation

sealed class ViewState<out T : Any>
class SuccessState<out T : Any>(val data: T) : ViewState<T>()
class ErrorState<out T : Any>(val message: String) : ViewState<T>()
class ExceptionState<out T : Any>(val error: Throwable) : ViewState<T>()
class LoadingState<out T : Any> : ViewState<T>()
