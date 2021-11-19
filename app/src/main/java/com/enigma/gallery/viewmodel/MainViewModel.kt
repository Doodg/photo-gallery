package com.enigma.gallery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enigma.gallery.model.PhotoResponseDetails
import com.enigma.gallery.model.PhotosResponse
import com.enigma.gallery.model.Status
import com.enigma.gallery.repo.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val galleryRepository: GalleryRepository) :
    ViewModel() {
    val imagesListLiveData = MutableLiveData<Status<PhotosResponse>>()
    private val disposables: CompositeDisposable = CompositeDisposable()

    init {
        getImages()
    }

    fun getImages(keyword: String = "fruits") {
        imagesListLiveData.postValue(Status.Loading)
        disposables.add(
            galleryRepository.getImagesRemote(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(GetSingleRadioFavoriteSubscriber())
        )
    }

    fun cacheResponse(listOfPhotos: List<PhotoResponseDetails>) {
        disposables.add(
            galleryRepository.saveImages(listOfPhotos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(AddPhotosToCacheObserver())
        )
    }

    private inner class GetSingleRadioFavoriteSubscriber : DisposableObserver<PhotosResponse>() {
        override fun onNext(images: PhotosResponse) {
            imagesListLiveData.postValue(Status.Success(images))
            cacheResponse(images.hits)
        }

        override fun onError(e: Throwable) {
            imagesListLiveData.postValue(Status.Error(e))
        }

        override fun onComplete() {

        }

    }

    private inner class AddPhotosToCacheObserver() :
        DisposableCompletableObserver() {
        override fun onComplete() {

        }

        override fun onError(e: Throwable) {

        }

    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}