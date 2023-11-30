package com.breno.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.breno.instagram.R
import com.breno.instagram.databinding.FragmentRegisterWelcomeBinding

class RegisterWelcomeFragment : Fragment() {

    companion object {
        const val BUNDLE_NAME = "name"
    }

    private var _binding: FragmentRegisterWelcomeBinding? = null
    private val binding get() = _binding!!

    private val name by lazy {
        arguments?.getString(BUNDLE_NAME).orEmpty()
    }

    private var fragmentAttachListener: FragmentAttachListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fRegisterWelcomeTxtWelcome.text = getString(R.string.welcome_to_instagram, name)

            fRegisterButtonEnter.apply {
                isEnabled = true
                setOnClickListener {
                    fragmentAttachListener?.goToPhotoScreen()
                }
            }
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
        _binding = null
    }
}