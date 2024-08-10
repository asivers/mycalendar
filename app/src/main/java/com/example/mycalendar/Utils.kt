package com.example.mycalendar

import android.annotation.SuppressLint
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ListPopupWindow
import android.widget.Spinner
import java.lang.reflect.Field

fun getOnItemSelectedListener(onItemSelected: Runnable) = object : OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {}
    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        onItemSelected.run()
    }
}

fun getOnSwipeListener(onSwipeLeft: Runnable, onSwipeRight: Runnable) = object: OnSwipeListener(null) {
    override fun onSwipeLeft() = onSwipeLeft.run()
    override fun onSwipeRight() = onSwipeRight.run()
}

@SuppressLint("DiscouragedPrivateApi")
fun shortenSpinnerPopup(spinner: Spinner, height: Int) {
    try {
        val popup: Field = Spinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val popupWindow: ListPopupWindow = popup.get(spinner) as ListPopupWindow
        popupWindow.height = height
    } catch (_: NoClassDefFoundError) {
    } catch (_: ClassCastException) {
    } catch (_: NoSuchFieldException) {
    } catch (_: IllegalAccessException) {
    }
}
