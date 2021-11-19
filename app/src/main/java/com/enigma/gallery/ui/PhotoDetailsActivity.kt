package com.enigma.gallery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.enigma.gallery.R
import com.enigma.gallery.model.PhotoResponseDetails
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.enigma.gallery.databinding.ActivityPhotoDetailsBinding
import com.squareup.picasso.Picasso


class PhotoDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPhotoDetailsBinding? =
            DataBindingUtil.setContentView(this, R.layout.activity_photo_details)
        setSupportActionBar(binding?.photoDetailsToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        intent.let {
            Picasso.get()
                .load(intent.extras?.getString("photoLink"))
                .into(binding?.photoImageViewLarge)
            binding?.nameTextView?.text = intent.extras?.getString("userName")
            binding?.photoTagsTextView?.text =
                String.format(getString(R.string.tags), intent.extras?.getString("photoTags"))
            binding?.numberOfLikesTextView?.text =
                String.format(getString(R.string.likes), intent.extras?.getInt("photoLikes"))
            binding?.numberOfDownloadsTextView?.text =
                String.format(getString(R.string.download), intent.extras?.getInt("photoDownloads"))
            binding?.numberOfCommentsTextView?.text =
                String.format(getString(R.string.comments), intent.extras?.getInt("photoComments"))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}