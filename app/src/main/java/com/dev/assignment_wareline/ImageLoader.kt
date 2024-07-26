package com.dev.assignment_wareline

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageLoader(private val context: Context) {

    private val memoryCache: LruCache<String, Bitmap>
    private val cacheDir: File = context.cacheDir

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }
        }
    }

    fun loadImage(url: String, onSuccess: (Bitmap) -> Unit, onError: (Exception) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = getBitmapFromCache(url) ?: getBitmapFromNetwork(url)
            if (bitmap != null) {
                onSuccess(bitmap)
            } else {
                onError(Exception("Failed to load image"))
            }
        }
    }

    private fun getBitmapFromCache(url: String): Bitmap? {
        return memoryCache.get(url) ?: getBitmapFromDisk(url)?.also {
            memoryCache.put(url, it)
        }
    }

    private suspend fun getBitmapFromNetwork(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val inputStream = response.body?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    if (bitmap != null) {
                        memoryCache.put(url, bitmap)
                        saveBitmapToDisk(url, bitmap)
                    }
                    bitmap
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun getBitmapFromDisk(url: String): Bitmap? {
        val file = File(cacheDir, url.hashCode().toString())
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }

    private fun saveBitmapToDisk(url: String, bitmap: Bitmap) {
        val file = File(cacheDir, url.hashCode().toString())
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
    }
}
