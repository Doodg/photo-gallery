package com.enigma.gallery.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enigma.gallery.model.PhotoResponseDetails

@Database(entities = [PhotoResponseDetails::class], version = 1)
abstract class GalleryDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}