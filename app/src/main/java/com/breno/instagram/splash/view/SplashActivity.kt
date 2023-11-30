package com.breno.instagram.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.breno.instagram.common.DependencyInjector
import com.breno.instagram.common.extesions.animation
import com.breno.instagram.databinding.ActivitySplashBinding
import com.breno.instagram.login.view.LoginActivity
import com.breno.instagram.main.view.MainActivity
import com.breno.instagram.splash.data.Splash
import com.breno.instagram.splash.presentation.SplashPresenter

class SplashActivity : AppCompatActivity(), Splash.View {

    override lateinit var presenter: Splash.Presenter

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SplashPresenter(this, DependencyInjector.splashRepository())
        binding.aSplashIvLogo.animation(
            block = {
                it.apply {
                    duration = 1000
                    alpha(1.0f)
                    start()
                }
            },
            doEndAnimation = {
                presenter.authenticated()
            }
        )
    }

    override fun goToMainScreen() {
        binding.aSplashIvLogo.animation(
            block = {
                it.apply {
                    duration = 1000
                    alpha(0.0f)
                    startDelay = 1000
                }
            },
            doEndAnimation = {
                Intent(baseContext, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            })
    }

    override fun goToLoginScreen() {
        binding.aSplashIvLogo.animation(
            block = {
                it.apply {
                    duration = 1000
                    alpha(0.0f)
                    startDelay = 1000
                }
            },
            doEndAnimation = {
                Intent(baseContext, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            })
    }
}