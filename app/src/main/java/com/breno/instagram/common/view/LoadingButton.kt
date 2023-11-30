package com.breno.instagram.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.use
import com.breno.instagram.R
import com.breno.instagram.databinding.ButtonLoadBinding


class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: ButtonLoadBinding

    private val button get() = binding.button
    private val progress get() = binding.progress
    private var textLoading: String? = null

    var showProgress: Boolean = false
        set(value) {
            if (value) showProgress()
            else hideProgress()
            field = value
        }

    init {
        inflateLayout()
        setupComponents(attrs, defStyleAttr)
    }

    private fun inflateLayout() {
        binding = ButtonLoadBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun setupComponents(attrs: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, defStyleAttr, 0).use {
            textLoading = it.getString(R.styleable.LoadingButton_textLoading)
            button.text = textLoading
            button.isEnabled = false
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        button.isEnabled = enabled
    }

    fun showProgress(enabled: Boolean) {
        if (enabled) showProgress()
        else hideProgress()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)

        button.setOnClickListener(l)
    }

    private fun showProgress() {
        button.apply {
            text = ""
            isEnabled = false
        }
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        button.apply {
            text = textLoading
            isEnabled = true
        }
        progress.visibility = View.GONE
    }
}