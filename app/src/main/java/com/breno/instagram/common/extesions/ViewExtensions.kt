package com.breno.instagram.common.extesions

import android.view.View
import android.view.ViewPropertyAnimator

fun View.animation(
    block: (animate: ViewPropertyAnimator) -> Unit,
    doEndAnimation: (animate: ViewPropertyAnimator) -> Unit
) {
    animate().apply {
        block.invoke(this)
        setListener(
            animationEnd {
                doEndAnimation.invoke(this)
            }
        )
    }
}