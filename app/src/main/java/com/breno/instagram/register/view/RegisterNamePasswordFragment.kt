package com.breno.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.databinding.FragmentRegisterNamePasswordBinding
import com.breno.instagram.register.RegisterNameAndPassword
import com.breno.instagram.register.presentation.RegisterNameAndPassowordPresenter

class RegisterNamePasswordFragment : Fragment(), RegisterNameAndPassword.View {

    companion object {
        const val BUNDLE_EMAIL = "email"
    }

    private var _binding: FragmentRegisterNamePasswordBinding? = null
    private val binding get() = _binding!!
    private val email by lazy {
        arguments?.getString(BUNDLE_EMAIL).orEmpty()
    }
    override lateinit var presenter: RegisterNameAndPassword.Presenter

    private var fragmentAttachListener: FragmentAttachListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterNamePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
            RegisterNameAndPassowordPresenter(this, DependencyInjector.registerEmailRepository())

        binding.apply {
            fRegisterNamePasswordTxtLogin.setOnClickListener {
                activity?.finish()
            }
            fRegisterNamePasswordButtonEnter.setOnClickListener {
                presenter.create(
                    email,
                    fRegisterNamePasswordName.text.toString(),
                    fRegisterNamePasswordPassword.text.toString(),
                    fRegisterNamePasswordPasswordConfirm.text.toString()
                )
            }

            fRegisterNamePasswordName.addTextChangedListener {
                displayNameFailure(null)
                fRegisterNamePasswordButtonEnter.isEnabled =
                    this.fRegisterNamePasswordName.text?.isNotEmpty() == true && this.fRegisterNamePasswordPassword.text?.isNotEmpty() == true && this.fRegisterNamePasswordPasswordConfirm.text?.isNotEmpty() == true
            }
            fRegisterNamePasswordPassword.addTextChangedListener {
                displayNameFailure(null)
                fRegisterNamePasswordButtonEnter.isEnabled =
                    this.fRegisterNamePasswordPassword.text?.isNotEmpty() == true && this.fRegisterNamePasswordName.text?.isNotEmpty() == true && this.fRegisterNamePasswordPasswordConfirm.text?.isNotEmpty() == true
            }
            fRegisterNamePasswordPasswordConfirm.addTextChangedListener {
                displayNameFailure(null)
                fRegisterNamePasswordButtonEnter.isEnabled =
                    this.fRegisterNamePasswordPasswordConfirm.text?.isNotEmpty() == true && this.fRegisterNamePasswordName.text?.isNotEmpty() == true && this.fRegisterNamePasswordPassword.text?.isNotEmpty() == true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding.fRegisterNamePasswordButtonEnter.showProgress = enabled
    }

    override fun displayNameFailure(nameError: Int?) {
        binding.fRegisterNamePasswordEditNameLayout.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.fRegisterNamePasswordEditPasswordLayout.error = passwordError?.let { getString(it) }
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateSucces(name: String) {
        fragmentAttachListener?.goToWelcomeScreen(name)
    }


}