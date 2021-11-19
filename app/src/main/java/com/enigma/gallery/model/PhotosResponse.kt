package com.enigma.gallery.model

import com.google.gson.annotations.SerializedName

   
data class PhotosResponse (
   @SerializedName("total") var total : Int,
   @SerializedName("totalHits") var totalHits : Int,
   @SerializedName("hits") var hits : List<PhotoResponseDetails>

)