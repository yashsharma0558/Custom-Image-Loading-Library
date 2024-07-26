package com.dev.assignment_wareline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.assignment_wareline.adapters.PhotoAdapter
import com.dev.assignment_wareline.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageLoader: ImageLoader
    private lateinit var photoAdapter: PhotoAdapter
    private val apiKey = "IGVcCPshKhN88aqV5LJD12omKJLaYUnEAz1m3ifl9exa9dEjpeAk0OFL"
    private var currentPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)  // 3 columns
        imageLoader = ImageLoader(this)
        photoAdapter = PhotoAdapter(mutableListOf(), imageLoader)
        recyclerView.adapter = photoAdapter

        // Initial load of two pages
        fetchImages()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Load more images when the user has scrolled to the last 30th item
                if (!isLoading && lastVisibleItem >= totalItemCount - 30) {
                    currentPage += 2
                    fetchImages()
                }
            }
        })
    }

    private fun fetchImages() {
        isLoading = true
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.getCuratedPhotos(apiKey, 80, currentPage)
                }
                photoAdapter.addPhotos(response.photos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}
