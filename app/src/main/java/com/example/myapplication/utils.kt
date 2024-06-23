package com.example.myapplication


import android.widget.ImageView
import com.bumptech.glide.Glide


fun loadImage(imageView: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrBlank()) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_default_photo)
            .error(R.drawable.ic_default_photo)
            .into(imageView)
    } else {
        Glide.with(imageView.context).clear(imageView)
        imageView.setImageResource(R.drawable.ic_default_photo)
    }
}