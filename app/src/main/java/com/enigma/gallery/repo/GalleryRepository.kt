package com.enigma.gallery.repo

import com.enigma.gallery.cache.PhotosDao
import com.enigma.gallery.model.PhotoResponseDetails
import com.enigma.gallery.remote.ApiService
import io.reactivex.Completable

class GalleryRepository(private val apiService: ApiService, private val photosDao: PhotosDao) {
    fun getImagesRemote(keyword: String) = apiService.getImagesFiltered(q = keyword)

    fun saveImages(photos: List<PhotoResponseDetails>): Completable {
        return photosDao.savePhotos(photos)
    }

}