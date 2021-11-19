package com.enigma.gallery.model

import com.google.gson.annotations.SerializedName
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.picasso.Picasso

@Entity
data class PhotoResponseDetails(

    @PrimaryKey @SerializedName("id") var id: Int,
    @SerializedName("pageURL") var pageURL: String?,
    @SerializedName("type") var type: String?,
    @SerializedName("tags") var tags: String?,
    @SerializedName("previewURL") var previewURL: String?,
    @SerializedName("previewWidth") var previewWidth: Int,
    @SerializedName("previewHeight") var previewHeight: Int,
    @SerializedName("webformatURL") var webformatURL: String?,
    @SerializedName("webformatWidth") var webformatWidth: Int,
    @SerializedName("webformatHeight") var webformatHeight: Int,
    @SerializedName("largeImageURL") var largeImageURL: String?,
    @SerializedName("fullHDURL") var fullHDURL: String?,
    @SerializedName("imageURL") var imageURL: String?,
    @SerializedName("imageWidth") var imageWidth: Int,
    @SerializedName("imageHeight") var imageHeight: Int,
    @SerializedName("imageSize") var imageSize: Int,
    @SerializedName("views") var views: Int,
    @SerializedName("downloads") var downloads: Int,
    @SerializedName("likes") var likes: Int,
    @SerializedName("comments") var comments: Int,
    @SerializedName("user_id") var userId: Int,
    @SerializedName("user") var user: String?,
    @SerializedName("userImageURL") var userImageURL: String?

) {

    companion object {
        @BindingAdapter("android:loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageURL: String?) {
            Picasso.get()
                .load(imageURL)
                .into(imageView)
        }
    }


}