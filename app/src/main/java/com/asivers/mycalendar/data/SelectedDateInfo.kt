package com.asivers.mycalendar.data

import com.asivers.mycalendar.utils.getCurrentDayOfMonth
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import java.util.GregorianCalendar

data class SelectedDateInfo(
    val year: Int = getCurrentYear(),
    val monthIndex: Int = getCurrentMonthIndex(),
    val dayOfMonth: Int = getCurrentDayOfMonth(),
    val yearOnMonthView: Int = year,
    val byDropdown: Boolean = false,
    val refreshFlag: Boolean = false // workaround to refresh state without changing the date
) {

    fun getDate(): GregorianCalendar = GregorianCalendar(year, monthIndex, dayOfMonth)

    fun cloneWithRefresh(): SelectedDateInfo = SelectedDateInfo(
        year = year,
        monthIndex = monthIndex,
        dayOfMonth = dayOfMonth,
        yearOnMonthView = yearOnMonthView,
        byDropdown = byDropdown,
        refreshFlag = !refreshFlag
    )

}
