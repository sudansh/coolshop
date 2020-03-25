package com.sudansh.coolshop.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


fun toBase64(bitmap: Bitmap): String {
    val bos = ByteArrayOutputStream()
    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        80,
        bos
    )
    return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT)
}

fun getCompressedBitmapData(bitmap: Bitmap, maxDimensions: Int): Bitmap? {
    val resizedBitmap = if (bitmap.width > maxDimensions || bitmap.height > maxDimensions) {
        getResizedBitmap(
            bitmap,
            maxDimensions
        )
    } else {
        bitmap
    }
    val bitmapData = getByteArray(resizedBitmap)
    return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size)
}

fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
    var width = image.width
    var height = image.height
    val bitmapRatio = width.toFloat() / height.toFloat()
    if (bitmapRatio > 1) {
        width = maxSize
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxSize
        width = (height * bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(
        image,
        width,
        height,
        true
    )
}

fun getByteArray(bitmap: Bitmap): ByteArray {
    val bos = ByteArrayOutputStream()
    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        80,
        bos
    )
    return bos.toByteArray()
}

fun String?.toMd5(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    val md5: MessageDigest
    return try {
        md5 = MessageDigest.getInstance("MD5")
        val bytes = md5.digest(this.toByteArray())
        bytesToHex(bytes)
    } catch (e: NoSuchAlgorithmException) {
        ""
    }
}

fun bytesToHex(byteArray: ByteArray): String {
    var result = ""
    var tmp: String
    for (byte in byteArray) {
        tmp = Integer.toHexString(byte.toInt() and 0xFF)
        if (tmp.length == 1) {
            result += "0"
        }
        result += tmp
    }
    return result
}
