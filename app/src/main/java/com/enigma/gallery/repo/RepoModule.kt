package com.enigma.gallery.repo

import com.enigma.gallery.cache.PhotosDao
import com.enigma.gallery.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService, photosDao: PhotosDao) =
        GalleryRepository(apiService, photosDao)
}