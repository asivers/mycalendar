package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.Spinner
import android.widget.TextView
import java.lang.reflect.Field
import java.time.Month

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

@SuppressLint("NewApi")
fun getYearSpinnerAdapter(
    context: Context,
    yearSpinner: Spinner,
    years: Array<Int>
) = object : ArrayAdapter<Int>(context, R.layout.year_spinner_header, years) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, null, parent)
        if (position == yearSpinner.selectedItemPosition) {
            val colorToSet = context.resources.getColor(R.color.gradient_month_bottom, null)
            (view as TextView).setTextColor(colorToSet)
            val fontToSet = context.resources.getFont(R.font.montserrat_medium)
            view.setTypeface(fontToSet)
        }
        return view
    }
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

fun isHoliday(
    index: Int,
    dateToSet: Int,
    month: Month,
    year: Int
): Boolean {
    if (index in holidayIndexes)
        return true
    if (month in holidayDatesEveryYear &&
        dateToSet in holidayDatesEveryYear[month]!!)
        return true
    if (year in holidayDatesOneTime &&
        month in holidayDatesOneTime[year]!! &&
        dateToSet in holidayDatesOneTime[year]!![month]!!) {
        return true
    }
    return false
}
