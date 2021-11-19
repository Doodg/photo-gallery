package com.enigma.gallery.remote

import com.enigma.gallery.BuildConfig
import com.enigma.gallery.model.PhotosResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/")
    fun getImagesFiltered(
        @Query("key") key: String = BuildConfig.SECRET_KEY,
        @Query("q") q: String
    ): Observable<PhotosResponse>
}