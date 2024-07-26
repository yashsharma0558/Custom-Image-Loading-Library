package com.dev.assignment_wareline.api

import com.dev.assignment_wareline.model.PexelsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

object RetrofitClient {
    private const val BASE_URL = "https://api.pexels.com/v1/"

    val instance: PexelsApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(PexelsApiService::class.java)
    }
}

interface PexelsApiService {
    @GET("curated")
    suspend fun getCuratedPhotos(
        @Header(
            "Authorization",
        ) apiKey: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): PexelsResponse
}
