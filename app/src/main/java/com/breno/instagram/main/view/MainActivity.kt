package com.breno.instagram.main.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.breno.instagram.R
import com.breno.instagram.common.extesions.replaceFragment
import com.breno.instagram.databinding.ActivityMainBinding
import com.breno.instagram.home.view.HomeFragment
import com.breno.instagram.post.view.AddFragment
import com.breno.instagram.profile.view.ProfileFragment
import com.breno.instagram.search.view.SearchFragment
import com.breno.instagram.splash.view.SplashActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    AddFragment.AddListener, SearchFragment.SearchListener, LogoutListener,
    ProfileFragment.FollowListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: Fragment
    private lateinit var addFragment: Fragment
    private lateinit var profileFragment: ProfileFragment
    private var currencyFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                    binding.aMainIvLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }

                Configuration.COLOR_MODE_HDR_NO -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
                    window.insetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }
        }


        setSupportActionBar(binding.aMainTToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        addFragment = AddFragment()
        profileFragment = ProfileFragment()

        binding.aMainBnv.setOnItemSelectedListener(this)
        binding.aMainBnv.selectedItemId = R.id.mBottomNavHome
    }

    private fun setScrollToolbarEnabled(enabled: Boolean) {
        val params = binding.aMainTToolbar.layoutParams as AppBarLayout.LayoutParams
        val coordinatorParams = binding.aMainAblBar.layoutParams as CoordinatorLayout.LayoutParams

        if (enabled) {
            params.scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatorParams.behavior = AppBarLayout.Behavior()
        } else {
            params.scrollFlags = 0
            coordinatorParams.behavior = null
        }

        binding.aMainAblBar.layoutParams = coordinatorParams
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var scrollToolbarEnabled = false

        when (item.itemId) {
            R.id.mBottomNavHome -> takeIf { currencyFragment != homeFragment }?.run {
                currencyFragment = homeFragment
            } ?: return false

            R.id.mBottomNavSearch -> takeIf { currencyFragment != searchFragment }?.run {
                currencyFragment = searchFragment
                scrollToolbarEnabled = false
            } ?: return false

            R.id.mBottomNavAdd -> takeIf { currencyFragment != addFragment }?.run {
                currencyFragment = addFragment
                scrollToolbarEnabled = false
            } ?: return false

            R.id.mBottomNavProfile -> {
                scrollToolbarEnabled = true
                takeIf { currencyFragment != profileFragment }?.run {
                    currencyFragment = profileFragment
                } ?: return false
            }
        }

        setScrollToolbarEnabled(scrollToolbarEnabled)

        currencyFragment?.let {
            replaceFragment(R.id.aMainFrag, it)
        }

        return true
    }

    override fun onPostCreated() {
        homeFragment.presenter.clear()

        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null) profileFragment.presenter.clear()

        goToFragHome()
    }

    private fun goToFragHome() {
        binding.aMainBnv.selectedItemId = R.id.mBottomNavHome
    }

    override fun goToProfile(uuid: String) {
        val fragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ProfileFragment.BUNDLE_USER_ID, uuid)
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.aMainFrag, fragment, fragment.javaClass.simpleName + "detail")
            addToBackStack(null)
            commit()
        }
    }

    override fun logout() {
        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null) profileFragment.presenter.clear()
        homeFragment.presenter.clear()
        homeFragment.presenter.logout()

        Intent(baseContext, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun followUpdated() {
        homeFragment.presenter.clear()

        if (supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null) {
            profileFragment.presenter.clear()
        }
    }
}