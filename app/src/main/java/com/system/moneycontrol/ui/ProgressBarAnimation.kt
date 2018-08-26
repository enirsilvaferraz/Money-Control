package com.system.moneycontrol.ui

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import androidx.appcompat.widget.LinearLayoutCompat

class ProgressBarAnimation(
        private val mProgressContainer: LinearLayoutCompat,
        private val mProgressBar: ProgressBar,
        fullDuration: Long) : Animation() {

    private var mTo: Int = 0
    private var mFrom: Int = 0
    private val mStepDuration: Long

    init {
        mStepDuration = fullDuration / mProgressBar.max
    }

    fun setProgress(progressParam: Int) {

        mProgressContainer.visibility = View.VISIBLE

        var progress = progressParam

        if (progress < 0) {
            progress = 0
        }

        if (progress > mProgressBar.max) {
            progress = mProgressBar.max
        }

        mTo = progress

        mFrom = mProgressBar.progress
        duration = Math.abs(mTo - mFrom) * mStepDuration
        mProgressBar.startAnimation(this)
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val value = mFrom + (mTo - mFrom) * interpolatedTime
        mProgressBar.progress = value.toInt()

        if (mProgressBar.progress == mProgressBar.max) {
            mProgressContainer.visibility = View.GONE
        }
    }
}