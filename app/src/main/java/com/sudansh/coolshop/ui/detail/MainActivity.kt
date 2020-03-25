package com.sudansh.coolshop.ui.detail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sudansh.coolshop.R
import com.sudansh.coolshop.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val cameraProviderFuture by lazy {
        ProcessCameraProvider.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getDetail()

        binding.image.setOnClickListener { openChooseialog() }

    }

    private fun openChooseialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Upload Photo")
            .setItems(R.array.picker) { _, which ->
                if (which == 0) {
                    checkCameraPermission()
                } else if (which == 1) {
                    useGallery()
                }
            }.create().show()
    }

    private fun checkCameraPermission() {
        if (allPermissionsGranted()) {
            takePicture()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (allPermissionsGranted()) {
                takePicture()
            } else {
                Toast.makeText(this, "Camera Permission not granted", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(
                    takePictureIntent,
                    REQUEST_IMAGE_CAPTURE
                )
            }
        }
    }


    private fun allPermissionsGranted() = listOf(Manifest.permission.CAMERA).all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap: Bitmap = data.extras?.get("data") as? Bitmap ?: return
                    viewModel.compressImageAndUpload(imageBitmap)
                }
                REQUEST_IMAGE_GALLERY -> {
                    data.data?.let { uri ->
                        val imageBitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                        viewModel.uploadAvatar(imageBitmap)
                    }
                }
            }
        }
    }

    private fun useGallery() {
        val i = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(
            i,
            REQUEST_IMAGE_GALLERY
        )
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 101
        const val REQUEST_IMAGE_GALLERY = 102
    }

}