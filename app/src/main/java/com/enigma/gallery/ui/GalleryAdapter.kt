package com.enigma.gallery.ui

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.enigma.gallery.R
import com.enigma.gallery.databinding.GalleryListItemBinding
import com.enigma.gallery.model.PhotoResponseDetails

class GalleryAdapter(private val onItemClick: (PhotoResponseDetails) -> Unit) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private var mLastClickTime: Long = 0
    private var listOfPhotos = ArrayList<PhotoResponseDetails?>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryViewHolder {
        val binding: GalleryListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.gallery_list_item,
            parent,
            false
        )
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(listOfPhotos[position])
        holder.itemView.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener;
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            listOfPhotos[position]?.let { it1 -> onItemClick(it1) }
        }
    }

    override fun getItemCount(): Int {
        return listOfPhotos.size
    }

    fun setPhotos(photosList: List<PhotoResponseDetails>) {
        if (listOfPhotos.isNotEmpty()) listOfPhotos.clear()
        listOfPhotos.addAll(photosList)
        notifyDataSetChanged()
    }

    class GalleryViewHolder(private val binding: GalleryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imagesResponse: PhotoResponseDetails?) {
            binding.photoItem = imagesResponse
            binding.executePendingBindings()
        }
    }
}
