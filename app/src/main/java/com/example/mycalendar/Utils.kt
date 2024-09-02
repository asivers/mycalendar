package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListPopupWindow
import android.widget.Spinner
import android.widget.TextView
import java.lang.reflect.Field
import java.time.LocalDate
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

@SuppressLint("NewApi")
fun getYearSpinnerAdapter(
    context: Context,
    yearSpinner: Spinner,
    years: Array<Int>
) = object : ArrayAdapter<Int>(context, R.layout.myv_year_spinner_header, years) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, null, parent)
        if (position == yearSpinner.selectedItemPosition) {
            val colorToSet = context.resources.getColor(R.color.mv_gradient_bottom, null)
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

fun <T: View> setDayElementsForMonth(
    dayElements: Array<T>,
    monthValue: Int,
    year: Int,
    weekdayColor: Int,
    holidayColor: Int,
    todayCircle: Drawable
) {
    if (!dayElements.isArrayOf<Button>() && !dayElements.isArrayOf<TextView>()) return

    val isButton = dayElements.isArrayOf<Button>()

    val firstOfThisMonth = LocalDate.of(year, monthValue, 1)
    val month = Month.of(monthValue)
    val monthLength = month.length(firstOfThisMonth.isLeapYear)

    val monthStartIndex = firstOfThisMonth.dayOfWeek.value - 1
    val monthEndIndex = monthStartIndex + monthLength

    for (i in (0..<monthStartIndex) + (monthEndIndex ..<42)) {
        dayElements[i].visibility = View.INVISIBLE
    }

    var dateToSet = 1
    for (i in monthStartIndex..<monthEndIndex) {
        val textColor = if (isHoliday(i, dateToSet, month, year)) holidayColor else weekdayColor
        val dayElement = if (isButton) dayElements[i] as Button else dayElements[i] as TextView
        dayElement.visibility = View.VISIBLE
        dayElement.setBackgroundResource(0)
        dayElement.setTextColor(textColor)
        dayElement.text = dateToSet++.toString()
    }

    val today = LocalDate.now()
    if (year == today.year && month == today.month) {
        val todayIndex = monthStartIndex - 1 + today.dayOfMonth
        val todayButton = dayElements[todayIndex]
        todayButton.background = todayCircle
    }
}

fun getMonthValueFromIntent(intent: Intent): Int? = intent.extras?.getInt("monthValue")
fun setMonthValueToIntent(intent: Intent, monthValue: Int) = intent.putExtra("monthValue", monthValue)

fun getYearFromIntent(intent: Intent): Int? = intent.extras?.getInt("year")
fun setYearToIntent(intent: Intent, year: Int) = intent.putExtra("year", year)
