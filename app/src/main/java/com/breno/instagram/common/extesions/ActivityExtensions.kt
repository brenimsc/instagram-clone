package com.breno.instagram.common.extesions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    val view = currentFocus ?: View(this)

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun animationEnd(block: () -> Unit): AnimatorListenerAdapter {
    return object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            block.invoke()
        }
    }
}

fun AppCompatActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    if (supportFragmentManager.findFragmentById(id) == null) {
        supportFragmentManager.beginTransaction().apply {
            add(id, fragment, fragment.javaClass.simpleName)
            commit()
        }
    } else {
        supportFragmentManager.beginTransaction().apply {
            replace(id, fragment, fragment.javaClass.simpleName)
            addToBackStack(null)
            commit()
        }
    }
    hideKeyboard()
}