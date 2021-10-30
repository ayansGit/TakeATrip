package com.takeatrip.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.takeatrip.R

class ProgressLoader(context: Context) : Dialog(context) {

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progressLoaderLayout = createLoadingLayout(context)
        val layoutParam = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT)
        setContentView(progressLoaderLayout, layoutParam)
    }

    private fun createLoadingLayout(context: Context): FrameLayout{
        val frameLayout = FrameLayout(context)
        frameLayout.setBackgroundColor(Color.TRANSPARENT)
        val progressBar = ProgressBar(context)
        val progressBarLp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT )
        progressBarLp.gravity = Gravity.CENTER
        progressBar.layoutParams = progressBarLp
        frameLayout.addView(progressBar)
        return frameLayout
    }

}