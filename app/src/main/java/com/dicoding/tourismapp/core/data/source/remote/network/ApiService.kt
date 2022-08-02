package com.dicoding.tourismapp.core.data.source.remote.network

import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {
    @GET("list")
    suspend fun getList(): TourismResponse //hapus Call, tambahkan suspend
}