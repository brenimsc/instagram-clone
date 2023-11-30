package com.breno.instagram.register.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.databinding.FragmentRegisterEmailBinding
import com.breno.instagram.register.RegisterEmail
import com.breno.instagram.register.presentation.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(), RegisterEmail.View {

    private var _binding: FragmentRegisterEmailBinding? = null
    private val binding get() = _binding!!

    private var fragmentAttachListener: FragmentAttachListener? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = RegisterEmailPresenter(this, DependencyInjector.registerEmailRepository())

        binding.apply {
            when (resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    fRegisterLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
            }
            fRegisterTxtLogin.setOnClickListener {
                activity?.finish()
            }
            fRegisterButtonEnter.setOnClickListener {
                presenter.create(fRegisterEditEmail.text.toString())
            }
            fRegisterEditEmail.addTextChangedListener {
                displayEmailFailure(null)
                fRegisterButtonEnter.isEnabled = it.toString().isNotEmpty()
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
        presenter.onDestroy()
        _binding = null
        fragmentAttachListener = null
    }

    override fun showProgress(enabled: Boolean) {
        binding.fRegisterButtonEnter.showProgress = enabled
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.fRegisterEditEmail.error = emailError?.let { getString(it) }
    }

    override fun onEmailFailure(message: String) {
        binding.fRegisterEditEmail.error = message
    }

    override fun goToNameAndPasswordScreen(email: String) {
        fragmentAttachListener?.goToNameAndPasswordScreen(email)
    }
}