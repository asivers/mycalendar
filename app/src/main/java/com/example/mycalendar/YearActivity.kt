package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.time.LocalDate
import java.time.Month

@SuppressLint("NewApi")
class YearActivity : ComponentActivity() {

    private lateinit var yearSpinner: Spinner
    private lateinit var monthsCellsWithNames: Array<LinearLayout>
    private lateinit var dayCells: Array<Array<TextView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_year)

        setForegroundGradient()

        initAllElements()

        setupOnSwipeListeners()

        setupYearSpinner()

        setDayCellsAttributes()
    }

    private fun setForegroundGradient() {
        val gradientDrawable = GradientDrawable().apply {
            orientation = GradientDrawable.Orientation.TOP_BOTTOM
            gradientType = GradientDrawable.LINEAR_GRADIENT
            shape = GradientDrawable.RECTANGLE
        }
        val topColor = resources.getColor(R.color.gradient_year_view_top, null)
        val centralColor = resources.getColor(R.color.gradient_year_view_center, null)
        val bottomColor = resources.getColor(R.color.gradient_year_view_bottom, null)
        gradientDrawable.setColors(
            intArrayOf(topColor, centralColor, bottomColor),
            floatArrayOf(0f, 0.2f, 1f)
        )
        gradientDrawable.cornerRadii = floatArrayOf(128f, 128f, 128f, 128f, 0f, 0f, 0f, 0f)
        findViewById<LinearLayout>(R.id.year_view_foreground_block).background = gradientDrawable
    }

    private fun initAllElements() {
        yearSpinner = findViewById(R.id.year_view_spinner)

        val yearCalendarLayout: GridLayout = findViewById(R.id.year_view_calendar_layout)
        monthsCellsWithNames = Array(12) {
            layoutInflater.inflate(R.layout.month_year_cell, yearCalendarLayout, false) as LinearLayout
        }
        monthsCellsWithNames.forEachIndexed { index, monthView ->
            yearCalendarLayout.addView(monthView)
            monthView.setOnClickListener { goToMonthPage() }
            val month = Month.of(index + 1)
            val nameToSetOnView = month.name[0] + month.name.substring(1).lowercase()
            (monthView.getChildAt(0) as TextView).text = nameToSetOnView
        }

        dayCells = Array(12) {
            val monthCell = monthsCellsWithNames[it].getChildAt(1) as GridLayout
            Array(42) {
                layoutInflater.inflate(R.layout.day_year_cell, monthCell, false) as TextView
            }
        }
        dayCells.forEachIndexed { index, dayCellsForMonth ->
            val monthCell = monthsCellsWithNames[index].getChildAt(1) as GridLayout
            dayCellsForMonth.forEach {
                monthCell.addView(it)
                it.setOnClickListener { goToMonthPage() }
            }
        }
    }

    private fun setupOnSwipeListeners() {
        val allElements: MutableList<View> = mutableListOf(
            findViewById(R.id.year_view_foreground_block),
            findViewById(R.id.year_view_top_layout),
            findViewById(R.id.year_view_calendar_layout),
            findViewById(R.id.year_view_label),
            yearSpinner
        )
        allElements.addAll(monthsCellsWithNames)
        allElements.addAll(dayCells.flatten())
        val onSwipeListener = getOnSwipeListener({ doOnSwipeLeft() }, { doOnSwipeRight() })
        allElements.forEach { it.setOnTouchListener(onSwipeListener) }
    }

    private fun setupYearSpinner() {
        val years = Array(201) { 1900 + it }
        val adapter = getYearSpinnerAdapter(this@YearActivity, yearSpinner, years)
        adapter.setDropDownViewResource(R.layout.year_spinner_item)
        yearSpinner.adapter = adapter
        val onItemSelectedListener = getOnItemSelectedListener { setDayCellsAttributes() }
        yearSpinner.onItemSelectedListener = onItemSelectedListener
        setSelectedYear(LocalDate.now().year)
        shortenSpinnerPopup(yearSpinner, 1600)
    }

    private fun setDayCellsAttributes() {
        val selectedYear = getSelectedYear()
        for (monthValue in 1..12) {
            val firstOfThisMonth = LocalDate.of(selectedYear, monthValue, 1)
            val month = Month.of(monthValue)
            val monthLength = month.length(firstOfThisMonth.isLeapYear)

            val monthStartIndex = firstOfThisMonth.dayOfWeek.value - 1
            val monthEndIndex = monthStartIndex + monthLength

            for (i in (0..<monthStartIndex) + (monthEndIndex ..<42)) {
                setDayCellDisappear(dayCells[monthValue - 1][i])
            }

            var dateToSet = 1
            for (i in monthStartIndex..<monthEndIndex) {
                val textColor = if (isHoliday(i, dateToSet, month, selectedYear))
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

    private fun doOnSwipeLeft() {
        val selectedYear = getSelectedYear()
        setSelectedYear(if (selectedYear < 2100) selectedYear + 1 else 2100)
        setDayCellsAttributes()
    }

    private fun doOnSwipeRight() {
        val selectedYear = getSelectedYear()
        setSelectedYear(if (selectedYear > 1900) selectedYear - 1 else 1900)
        setDayCellsAttributes()
    }

    private fun getSelectedYear() = yearSpinner.selectedItemPosition + 1900
    private fun setSelectedYear(year: Int) = yearSpinner.setSelection(year - 1900)

    private fun goToMonthPage() {
        val intent = Intent(this@YearActivity, MonthActivity::class.java)
        startActivity(intent)

        // todo consider this
//        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0, Color.TRANSPARENT)
        // and save month activity state (bundle)
    }
}
