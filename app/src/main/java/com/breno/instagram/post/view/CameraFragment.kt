package com.breno.instagram.post.view

import android.net.Uri
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.breno.instagram.R
import com.breno.instagram.add.Add
import com.breno.instagram.common.base.BaseFragment
import com.breno.instagram.common.util.Files
import com.breno.instagram.databinding.FragmentCameraBinding

class CameraFragment : BaseFragment<FragmentCameraBinding, Add.Presenter>(
    R.layout.fragment_camera,
    FragmentCameraBinding::bind
) {

    private lateinit var previewView: PreviewView

    private var imageCapture: ImageCapture? = null

    override lateinit var presenter: Add.Presenter

    override fun setupViews() {
        binding?.apply {
            previewView = fCameraPvPreview
            fCameraAcbButton.setOnClickListener {
                takePhoto()
            }
        }

        setFragmentResultListener("cameraKey") { requestKey, bundle ->
            val shouldStart = bundle.getBoolean("startCamera")

            if (shouldStart) {
                startCamera()
            }
        }
    }

    private fun takePhoto() {
        imageCapture?.run {
            val photoFile = Files.generateFile(requireActivity())
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(requireContext()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedUri = Uri.fromFile(photoFile)
                        setFragmentResult("takePhotoKey", bundleOf("uri" to savedUri))
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e("BRENOL", "Failed to take picture: ${exception.message}")
                    }

                })
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(480, 480))
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch (e: Exception) {
                Log.e("BRENOL", "Failure to camera: $e")
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun setupPresenter() {
    }

}