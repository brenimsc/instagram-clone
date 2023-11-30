package com.breno.instagram.login.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.databinding.ActivityLoginBinding
import com.breno.instagram.login.Login
import com.breno.instagram.login.presentation.LoginPresenter
import com.breno.instagram.main.view.MainActivity
import com.breno.instagram.register.view.RegisterActivity

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var binding: ActivityLoginBinding

    override lateinit var presenter: Login.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this, DependencyInjector.loginRepository())

        binding.apply {
            when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    aLoginLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
            }
            aLoginEditEmail.addTextChangedListener {
                displayEmailFailure(null)
                binding.aLoginButtonEnter.isEnabled =
                    it.toString().isNotEmpty() && aLoginEditPassword.text?.isNotEmpty() == true
            }
            aLoginEditPassword.addTextChangedListener {
                displayPasswordFailure(null)
                binding.aLoginButtonEnter.isEnabled =
                    it.toString().isNotEmpty() && aLoginEditEmail.text?.isNotEmpty() == true
            }
            aLoginButtonEnter.setOnClickListener {
                presenter.login(aLoginEditEmail.text.toString(), aLoginEditPassword.text.toString())
            }
            aLoginRegisterTxtLogin.setOnClickListener {
                goToRegisterScreen()
            }
        }
    }

    private fun goToRegisterScreen() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showProgress(enabled: Boolean) {
        binding.aLoginButtonEnter.showProgress = enabled
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.aLoginEditEmailLayout.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.aLoginEditPasswordLayout.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticate() {
        Intent(this, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(it)
        }
    }

    override fun onUserUnauthorized(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}