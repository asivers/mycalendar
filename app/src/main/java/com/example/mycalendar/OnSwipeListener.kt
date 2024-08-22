package com.example.mycalendar

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

fun getOnSwipeListener(
    onSwipeLeft: Runnable,
    onSwipeRight: Runnable,
    onSwipeTop: Runnable,
    onSwipeBottom: Runnable
) = object: OnSwipeListener(null) {
    override fun onSwipeLeft() = onSwipeLeft.run()
    override fun onSwipeRight() = onSwipeRight.run()
    override fun onSwipeTop() = onSwipeTop.run()
    override fun onSwipeBottom() = onSwipeBottom.run()
}

@Suppress("NOTHING_TO_OVERRIDE", "ACCIDENTAL_OVERRIDE")
open class OnSwipeListener(ctx: Context?) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    companion object {
        private const val SWIPE_THRESHOLD = 25
        private const val SWIPE_VELOCITY_THRESHOLD = 25
    }

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val onSwipe = gestureDetector.onTouchEvent(event)
        if (!onSwipe && (event.action == MotionEvent.ACTION_UP)) {
            v.performClick()
        }
        return true
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else {
                    if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom()
                        } else {
                            onSwipeTop()
                        }
                        result = true
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }
    }

    open fun onSwipeLeft() {}

    open fun onSwipeRight() {}

    open fun onSwipeTop() {}

    open fun onSwipeBottom() {}
}
