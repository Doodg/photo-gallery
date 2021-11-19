package com.enigma.gallery.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.enigma.gallery.viewmodel.MainViewModel
import com.enigma.gallery.R
import com.enigma.gallery.model.Status
import com.enigma.gallery.databinding.ActivityMainBinding
import com.enigma.gallery.model.PhotoResponseDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainActivityBinding: ActivityMainBinding
    private val galleryAdapter by lazy {
        GalleryAdapter(::onItemClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mainActivityBinding.galleryToolBar)
        mainActivityBinding.photosRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = galleryAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        startImageObserver()
        mainActivityBinding.tapToRefreshTextView.setOnClickListener {
            searchForImageByKeyWord("fruits")
        }
        mainActivityBinding.tagsSearchEditText.doOnTextChanged { text, start, before, count ->
            if (count > 0)
                searchForImageByKeyWord(text.toString())

        }
        mainActivityBinding.tagsSearchEditText.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow((currentFocus ?: View(baseContext)).windowToken, 0)
                    return true;
                }
                return false;
            }
        })
    }

    private fun startImageObserver() {
        mainViewModel.imagesListLiveData.observe(this, {
            when (it) {
                is Status.Loading -> {
                    mainActivityBinding.tapToRefreshTextView.visibility = View.GONE
                    mainActivityBinding.progressBar.visibility = View.VISIBLE
                }
                is Status.Success -> {
                    mainActivityBinding.tapToRefreshTextView.visibility = View.GONE
                    mainActivityBinding.progressBar.visibility = View.GONE
                    if (it.data.hits.isEmpty()) {
                        mainActivityBinding.photosRecyclerView.visibility = View.GONE
                        mainActivityBinding.noDataTextView.visibility = View.VISIBLE
                    } else {
                        mainActivityBinding.noDataTextView.visibility = View.GONE
                        mainActivityBinding.photosRecyclerView.visibility = View.VISIBLE
                        galleryAdapter.setPhotos((it.data.hits))
                    }
                }
                is Status.Error -> {
                    mainActivityBinding.tapToRefreshTextView.visibility = View.VISIBLE
                    mainActivityBinding.progressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_LONG)
                        .show()

                }
            }
        })
    }

    private fun searchForImageByKeyWord(keyWord: String) {
        mainViewModel.getImages(keyWord)
    }

    private fun onItemClick(photoResponseDetails: PhotoResponseDetails) {
        val seePhotoDetailsDialogBuilder = AlertDialog.Builder(this)
        seePhotoDetailsDialogBuilder.setTitle(getString(R.string.dialog_title))
        seePhotoDetailsDialogBuilder.setMessage(getString(R.string.dialog_message))
        seePhotoDetailsDialogBuilder.setCancelable(true)
        seePhotoDetailsDialogBuilder.setPositiveButton(
            getString(R.string.yes)
        ) { dialog, _ ->
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra("photoTags", photoResponseDetails.tags)
            intent.putExtra("photoLink", photoResponseDetails.previewURL)
            intent.putExtra("userName", photoResponseDetails.user)
            intent.putExtra("photoLikes", photoResponseDetails.likes)
            intent.putExtra("photoDownloads", photoResponseDetails.downloads)
            intent.putExtra("photoComments", photoResponseDetails.comments)
            startActivity(intent)
            dialog.dismiss()
        }
        seePhotoDetailsDialogBuilder.setNegativeButton(
            getString(R.string.close)
        ) { dialog, _ ->
            dialog.cancel()
        }
        seePhotoDetailsDialogBuilder.show()
    }
}