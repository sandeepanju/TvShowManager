package com.example.tvshowmanager.di

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor() : Interceptor {
    // as of now using hardcoded value due to time bound
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "X-Parse-Application-Id",
                "AaQjHwTIQtkCOhtjJaN/nDtMdiftbzMWW5N8uRZ+DNX9LI8AOziS10eHuryBEcCI"
            )
            .addHeader(
                "X-Parse-Master-Key",
                "yiCk1DW6WHWG58wjj3C4pB/WyhpokCeDeSQEXA5HaicgGh4pTUd+3/rMOR5xu1Yi"
            )
            .build()
        return chain.proceed(request)
    }

}