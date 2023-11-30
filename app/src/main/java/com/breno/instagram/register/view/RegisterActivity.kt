package com.breno.instagram.register.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.breno.instagram.R
import com.breno.instagram.common.extesions.replaceFragment
import com.breno.instagram.common.view.CropperImageFragment
import com.breno.instagram.databinding.ActivityRegisterBinding
import com.breno.instagram.main.view.MainActivity
import com.breno.instagram.register.view.RegisterNamePasswordFragment.Companion.BUNDLE_EMAIL
import com.breno.instagram.register.view.RegisterWelcomeFragment.Companion.BUNDLE_NAME
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var currentPhoto: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RegisterEmailFragment()
        replaceFragment(fragment)
    }

    override fun goToNameAndPasswordScreen(email: String) {
        replaceFragment(RegisterNamePasswordFragment().apply {
            arguments = Bundle().apply { putString(BUNDLE_EMAIL, email) }
        })
    }

    override fun goToWelcomeScreen(name: String) {
        replaceFragment(RegisterWelcomeFragment().apply {
            arguments = Bundle().apply { putString(BUNDLE_NAME, name) }
        })
    }

    override fun goToPhotoScreen() {
        replaceFragment(RegisterPhotoFragment())
    }

    override fun goToMainScreen() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(this)
        }
    }

    private val getCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { saved ->
            if (saved) {
                openImageCropper(currentPhoto)
            }
        }

    override fun goToCameraScreen() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)?.let {
            val photoFile = try {
                createImageFile()
            } catch (e: IOException) {
                Log.e("BRENOL", e.message, e)
                null
            }

            photoFile?.also {
                FileProvider.getUriForFile(this, "com.breno.instagram.fileprovider", it).run {
                    currentPhoto = this

                    getCamera.launch(this)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", dir)
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { openImageCropper(it) }
        }

    override fun goToGalleryScreen() {
        getContent.launch("image/*")
    }

    private fun replaceFragment(fragment: Fragment) {
        replaceFragment(R.id.aRegisterFragment, fragment)
    }

    private fun openImageCropper(uri: Uri) {
        val fragment = CropperImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CropperImageFragment.BUNDLE_URI, uri)
            }
        }
        replaceFragment(fragment)
    }
}