package com.system.moneycontrol.ui.component

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

abstract class RightDrawableOnTouchListener : View.OnTouchListener {

    private val fuzz = 50

    /*
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    override fun onTouch(v: View, event: MotionEvent): Boolean {

        var drawable: Drawable? = null

        val drawables = (v as TextView).compoundDrawables
        if (drawables.size == 4)
            drawable = drawables[2]

        if (event.action == MotionEvent.ACTION_DOWN && drawable != null) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            val bounds = drawable.bounds
            if (x >= v.right - bounds.width() - fuzz && x <= v.right - v.paddingRight + fuzz
                    && y >= v.paddingTop - fuzz && y <= v.height - v.paddingBottom + fuzz) {

                event.action = MotionEvent.ACTION_CANCEL

                onDrawableTouch()
                return true
            }
        }
        return false
    }

    abstract fun onDrawableTouch()

}