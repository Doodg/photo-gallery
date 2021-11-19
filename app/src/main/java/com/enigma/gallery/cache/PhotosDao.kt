package com.enigma.gallery.cache

import androidx.room.Dao
import androidx.room.Insert
import com.enigma.gallery.model.PhotoResponseDetails
import io.reactivex.Completable

@Dao
interface PhotosDao {

    @Insert
    fun savePhotos(photos: List<PhotoResponseDetails>): Completable
}