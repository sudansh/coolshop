package com.sudansh.coolshop.util


import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.textfield.TextInputLayout
import com.sudansh.coolshop.R

@BindingAdapter("imageUrl")
fun ImageView.setImage(url: String?) {
    val glideBuilder = GlideApp.with(this)
        .load(url)
    loadimage(glideBuilder)
}


fun ImageView.setImage(uri: Uri?) {
    val glideBuilder = GlideApp.with(this)
        .load(uri)
    loadimage(glideBuilder)
}

fun ImageView.setImage(bitmap: Bitmap?) {
    val glideBuilder = GlideApp.with(this)
        .load(bitmap)
    loadimage(glideBuilder)
}

private fun ImageView.loadimage(glideBuilder: GlideRequest<Drawable>) {
    val circularProgressDrawable = CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
    glideBuilder.override(200, 200)
        .placeholder(circularProgressDrawable)
        .transform(/*ColorFilterTransformation(Color.argb(80, 255, 0, 0)),*/ CenterCrop(),
            CircleCrop()
        )
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .fallback(R.drawable.ic_person)
        .into(this)
}

fun applyGscale(src: Bitmap): Bitmap? {
    // constant factors
    val GS_RED = 0.299
    val GS_GREEN = 0.587
    val GS_BLUE = 0.114

    // create output bitmap
    val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
    // pixel information
    var A: Int
    var R: Int
    var G: Int
    var B: Int
    var pixel: Int

    // get image size
    val width = src.width
    val height = src.height

    // scan through every single pixel
    (0 until width).forEach { x ->
        (0 until height).forEach { y ->
            // get one pixel color
            pixel = src.getPixel(x, y)
            // retrieve color of all channels
            A = Color.alpha(pixel)
            R = Color.red(pixel)
            G = Color.green(pixel)
            B = Color.blue(pixel)
            // take conversion up to one single value
            B = (GS_RED * R + GS_GREEN * G + GS_BLUE * B).toInt()
            G = B
            R = G
            // set new pixel color to output bitmap
            bmOut.setPixel(x, y, Color.argb(A, R, G, B))
        }
    }

    // return final image
    return bmOut
}

@BindingAdapter("error")
fun TextInputLayout.setError(@StringRes stringRes: Int?) {
    if (stringRes == null) this.error = ""
    else this.error = this.context.getString(stringRes)
}