package com.dev.assignment_wareline.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dev.assignment_wareline.ImageLoader
import com.dev.assignment_wareline.R
import com.dev.assignment_wareline.model.Photo

class PhotoAdapter(
    private val photos: MutableList<Photo>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo, imageLoader)
    }

    override fun getItemCount(): Int = photos.size

    fun addPhotos(newPhotos: List<Photo>) {
        val startPosition = photos.size
        photos.addAll(newPhotos)
        notifyItemRangeInserted(startPosition, newPhotos.size)
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(photo: Photo, imageLoader: ImageLoader) {
            imageLoader.loadImage(photo.src.medium, { bitmap ->
                imageView.setImageBitmap(bitmap)
            }, { error ->
                // handle error
            })
        }
    }
}
