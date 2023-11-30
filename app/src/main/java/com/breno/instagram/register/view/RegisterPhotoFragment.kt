package com.breno.instagram.register.view

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.breno.instagram.R
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.common.view.CropperImageFragment.Companion.BUNDLE_URI
import com.breno.instagram.common.view.CropperImageFragment.Companion.CROP_KEY
import com.breno.instagram.common.view.CustomDialog
import com.breno.instagram.databinding.FragmentRegisterPhotoBinding
import com.breno.instagram.register.RegisterPhoto
import com.breno.instagram.register.presentation.RegisterPhotoPresenter

class RegisterPhotoFragment : Fragment(), RegisterPhoto.View {

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(
            android.Manifest.permission.CAMERA
        )
    }

    private var _binding: FragmentRegisterPhotoBinding? = null
    private val binding get() = _binding!!

    override lateinit var presenter: RegisterPhoto.Presenter

    private var fragmentAttachListener: FragmentAttachListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(CROP_KEY) { requestKey, bundle ->
            val uri = bundle.getParcelable<Uri>(BUNDLE_URI)
            onCropImageResult(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = RegisterPhotoPresenter(this, DependencyInjector.registerEmailRepository())

        binding.apply {
            when (resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    fRegisterCivPhotoProfile.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
            }
            fRegisterPhotoBtnJump.setOnClickListener {
                fragmentAttachListener?.goToMainScreen()
            }
            fRegisterPhotoButtonAddPhoto.run {
                isEnabled = true

                setOnClickListener {
                    openDialog()
                }
            }
        }
    }

    private fun onCropImageResult(uri: Uri?) {
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
            binding.fRegisterCivPhotoProfile.setImageBitmap(bitmap)
            presenter.updateUser(it)
        }
    }

    private fun openDialog() {
        val customDialog = CustomDialog(requireContext())
        customDialog.addButton(R.string.photo, R.string.gallery) {
            when (it.id) {
                R.string.photo -> {
                    if (allPermissionsGranted()) {
                        fragmentAttachListener?.goToCameraScreen()
                    } else {
                        getPermission.launch(REQUIRED_PERMISSION)
                    }
                }

                R.string.gallery -> {
                    fragmentAttachListener?.goToGalleryScreen()
                }
            }
        }

        customDialog.show()
    }

    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION[0]
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val getPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (allPermissionsGranted()) {
                fragmentAttachListener?.goToCameraScreen()
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.permission_camera_denied,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
        _binding = null
    }

    override fun showProgress(enabled: Boolean) {
        binding.fRegisterPhotoButtonAddPhoto.showProgress = enabled
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSuccess() {
        fragmentAttachListener?.goToMainScreen()
    }
}