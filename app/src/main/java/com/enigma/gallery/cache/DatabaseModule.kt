package com.enigma.gallery.cache

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun providePhotoDao(appDatabase: GalleryDatabase): PhotosDao {
        return appDatabase.photosDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GalleryDatabase {
        return Room.databaseBuilder(
            appContext,
            GalleryDatabase::class.java,
            "photos_db"
        ).build()
    }
}