package com.breno.instagram.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.breno.instagram.databinding.FragmentImageCropperBinding
import java.io.File

class CropperImageFragment : Fragment() {

    companion object {
        const val BUNDLE_URI = "uri"
        const val CROP_KEY = "cropKey"
    }

    private var _binding: FragmentImageCropperBinding? = null
    private val binding get() = _binding!!

    private val uri by lazy {
        arguments?.getParcelable<Uri>(BUNDLE_URI)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageCropperBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fImageCropperCivContainer.apply {
                setAspectRatio(1, 1)
                setFixedAspectRatio(true)
                setImageUriAsync(uri)

                setOnCropImageCompleteListener { view, result ->
                    setFragmentResult(CROP_KEY, bundleOf(BUNDLE_URI to result.uri))
                    parentFragmentManager.popBackStack()
                }
            }
            fImageCropperBtnCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            fImageCropperBtnSave.setOnClickListener {
                val dir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                dir?.let {
                    val uriToSaved = Uri.fromFile(
                        File(
                            dir.path,
                            System.currentTimeMillis().toString() + ".jpeg"
                        )
                    )
                    fImageCropperCivContainer.saveCroppedImageAsync(uriToSaved)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}