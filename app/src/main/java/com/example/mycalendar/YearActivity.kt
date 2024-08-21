package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.time.LocalDate
import java.time.Month

// just a template yet
@SuppressLint("NewApi")
class YearActivity : ComponentActivity() {

    private lateinit var yearCalendarLayout: GridLayout
    private lateinit var monthsWithNames: Array<LinearLayout>
    private lateinit var dayCells: Array<Array<TextView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_year)

        yearCalendarLayout = findViewById(R.id.year_calendar_layout)
        monthsWithNames = Array(12) { layoutInflater.inflate(R.layout.month_year_cell, yearCalendarLayout, false) as LinearLayout }
        monthsWithNames.forEachIndexed { index, monthView ->
            yearCalendarLayout.addView(monthView)
            monthView.setOnClickListener { goToMonthPage() }
            val month = Month.of(index + 1)
            val nameToSetOnView = month.name[0] + month.name.substring(1).lowercase()
            (monthView.getChildAt(0) as TextView).text = nameToSetOnView
        }
        dayCells = Array(12) {
            val monthCell = monthsWithNames[it].getChildAt(1) as GridLayout
            Array(42) { layoutInflater.inflate(R.layout.day_year_cell, monthCell, false) as TextView }
        }
        dayCells.forEachIndexed { index, dayCellsForMonth ->
            val monthCell = monthsWithNames[index].getChildAt(1) as GridLayout
            dayCellsForMonth.forEach {
                monthCell.addView(it)
                it.setOnClickListener { goToMonthPage() }
            }
        }
        fillDayCells()
    }

    private fun fillDayCells() {
        val year = 2024
        for (monthValue in 1..12) {
            val firstOfSelectedMonth = LocalDate.of(year, monthValue, 1)
            val selectedMonth = Month.of(monthValue)
            val selectedMonthLength = selectedMonth.length(firstOfSelectedMonth.isLeapYear)

            val monthStartIndex = firstOfSelectedMonth.dayOfWeek.value - 1
            val monthEndIndex = monthStartIndex + selectedMonthLength

            for (i in (0..<monthStartIndex) + (monthEndIndex ..<42)) {
                setDayCellDisappear(dayCells[monthValue - 1][i])
            }

            var dateToSet = 1
            for (i in monthStartIndex..<monthEndIndex) {
                val textColor = if (isHoliday(i, dateToSet, selectedMonth, year))
                    R.color.green_day_holiday else R.color.white
                setDayCellParams(dayCells[monthValue - 1][i], dateToSet++, textColor)
            }
        }
    }

    private fun setDayCellDisappear(dayCell: TextView) {
        dayCell.visibility = View.GONE
    }

    private fun setDayCellParams(dayCell: TextView, dateToSet: Int, textColor: Int) {
        dayCell.visibility = View.VISIBLE
        dayCell.setBackgroundResource(0)
        dayCell.text = dateToSet.toString()
        dayCell.setTextColor(resources.getColor(textColor, null))
    }

    private fun goToMonthPage() {
        val intent = Intent(this@YearActivity, MonthActivity::class.java)
        startActivity(intent)

        // todo consider this
//        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0, Color.TRANSPARENT)
        // and save month activity state (bundle)
    }
}