package com.numero.sojodia.resource.remote

import retrofit2.Call
import com.numero.sojodia.model.Result

fun <T> Call<T>.executeSync(): Result<T> {
    return try {
        val response = execute()
        return if (response.isSuccessful) {
            val body = response.body() ?: return Result.Error(Exception("No response"))
            return Result.Success(body)
        } else {
            Result.Error(Exception("status code : ${response.code()}"))
        }
    } catch (t: Throwable) {
        Result.Error(t)
    }
}