package com.example.mycalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import java.time.LocalDate
import java.time.Month

@SuppressLint("NewApi")
class YearActivity : ComponentActivity() {

    private lateinit var yearSpinner: Spinner
    private val monthsCellsWithNames: MutableList<LinearLayout> = mutableListOf()
    private val dayCells: Array<MutableList<TextView>> = Array(12) { mutableListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.yv_activity)
        inflateLayoutAsynchronously()
        switchToMonthView(LocalDate.now().year, LocalDate.now().monthValue)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (!this::yearSpinner.isInitialized) {
            initAllElements()
        }
        setSelectedYear(getYearFromIntent(intent) ?: LocalDate.now().year)
        setDayCellsAttributes()
    }

    private fun inflateLayoutAsynchronously() {
        val inflater = AsyncLayoutInflater(this@YearActivity)
        val yearLayout: GridLayout = findViewById(R.id.yv_calendar_layout)
        for (i in 0..11) {
            inflater.inflate(R.layout.yv_month_cell_with_name, yearLayout) { monthCellWithName, _, _ ->
                monthsCellsWithNames.add(monthCellWithName as LinearLayout)
                yearLayout.addView(monthCellWithName)
                for (j in 0..41) {
                    val monthCell = monthCellWithName.getChildAt(1) as GridLayout
                    inflater.inflate(R.layout.yv_day_cell, monthCell) { dayCell, _, _ ->
                        dayCells[i].add(dayCell as TextView)
                        monthCell.addView(dayCell)
                    }
                }
            }
        }
    }

    private fun initAllElements() {
        waitUntilLayoutInflationFinished()
        setForegroundGradient()
        setupMonthNames()
        setupYearSpinner()
        setupOnSwipeListeners()
        setupOnClickListeners()
        setDayCellsAttributes()
    }

    private fun waitUntilLayoutInflationFinished() {
        while (!isLayoutInflationFinished()) {
            Thread.sleep(100)
        }
    }

    private fun isLayoutInflationFinished(): Boolean {
        if (monthsCellsWithNames.size < 12) {
            return false
        }
        dayCells.forEach {
            if (it.size < 42) {
                return false
            }
        }
        return true
    }

    private fun setForegroundGradient() {
        val gradientDrawable = GradientDrawable().apply {
            orientation = GradientDrawable.Orientation.TOP_BOTTOM
            gradientType = GradientDrawable.LINEAR_GRADIENT
            shape = GradientDrawable.RECTANGLE
        }
        val topColor = resources.getColor(R.color.yv_gradient_top, null)
        val centralColor = resources.getColor(R.color.yv_gradient_center, null)
        val bottomColor = resources.getColor(R.color.yv_gradient_bottom, null)
        gradientDrawable.setColors(
            intArrayOf(topColor, centralColor, bottomColor),
            floatArrayOf(0f, 0.2f, 1f)
        )
        gradientDrawable.cornerRadii = floatArrayOf(128f, 128f, 128f, 128f, 0f, 0f, 0f, 0f)
        findViewById<LinearLayout>(R.id.yv_foreground_layout).background = gradientDrawable
    }

    private fun setupMonthNames() {
        monthsCellsWithNames.forEachIndexed { index, monthView ->
            val month = Month.of(index + 1)
            val nameToSetOnView = month.name[0] + month.name.substring(1).lowercase()
            (monthView.getChildAt(0) as TextView).text = nameToSetOnView
        }
    }

    private fun setupYearSpinner() {
        yearSpinner = findViewById(R.id.yv_spinner_year)
        val years = Array(201) { 1900 + it }
        val adapter = getYearSpinnerAdapter(this@YearActivity, yearSpinner, years)
        adapter.setDropDownViewResource(R.layout.myv_year_spinner_item)
        yearSpinner.adapter = adapter
        val onItemSelectedListener = getOnItemSelectedListener { setDayCellsAttributes() }
        yearSpinner.onItemSelectedListener = onItemSelectedListener
        setSelectedYear(LocalDate.now().year)
        shortenSpinnerPopup(yearSpinner, 1600)
    }

    private fun setupOnSwipeListeners() {
        val allForegroundElements: MutableList<View> = mutableListOf(
            findViewById(R.id.yv_foreground_layout),
            findViewById(R.id.yv_header_layout),
            findViewById(R.id.yv_calendar_layout),
            findViewById(R.id.yv_label),
            yearSpinner
        )
        allForegroundElements.addAll(monthsCellsWithNames)
        allForegroundElements.addAll(dayCells.toList().flatten())
        val onSwipeListener = getOnSwipeListener(
            { doOnSwipeLeft() },
            { doOnSwipeRight() },
            {},
            { doOnSwipeBottom() }
        )
        allForegroundElements.forEach { it.setOnTouchListener(onSwipeListener) }
    }

    private fun setupOnClickListeners() {
        monthsCellsWithNames.forEachIndexed { index, monthView ->
            monthView.setOnClickListener { switchToMonthView(getSelectedYear(), index + 1) }
        }
        dayCells.forEachIndexed { index, dayCellsForMonth ->
            dayCellsForMonth.forEach {
                it.setOnClickListener { switchToMonthView(getSelectedYear(), index + 1) }
            }
        }
    }

    private fun setDayCellsAttributes() {
        val selectedYear = getSelectedYear()
        val todayCircle = getTodayCircle()
        for (monthValue in 1..12) {
            val dayElements = dayCells[monthValue - 1]
            val weekdayColor = resources.getColor(R.color.white, null)
            val holidayColor = resources.getColor(R.color.myv_green_day_holiday, null)
            setDayElementsForMonth(
                dayElements.toTypedArray(),
                monthValue,
                selectedYear,
                weekdayColor,
                holidayColor,
                todayCircle
            )
            if (selectedYear == getYearFromIntent(intent) && monthValue == getMonthValueFromIntent(intent)) {
                val currentMonthBackground = getDrawable(R.drawable.yv_selected_month)
                monthsCellsWithNames[monthValue - 1].background = currentMonthBackground
            } else {
                monthsCellsWithNames[monthValue - 1].setBackgroundResource(0)
            }
        }
    }

    private fun getTodayCircle(): LayerDrawable {
        val todayCircle = getDrawable(R.drawable.yv_today_circle) as LayerDrawable
        val width = dayCells[0][0].width
        val height = dayCells[0][0].height
        if (width < height) {
            todayCircle.setLayerWidth(0, width)
            todayCircle.setLayerHeight(0, width)
            todayCircle.setLayerInsetTop(0, (height - width) / 2 + 1)
        } else {
            todayCircle.setLayerWidth(0, height)
            todayCircle.setLayerHeight(0, height)
            todayCircle.setLayerInsetTop(0, 1)
            todayCircle.setLayerInsetLeft(0, (width - height) / 2)
        }
        return todayCircle
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

    private fun doOnSwipeBottom() {
        val year = getYearFromIntent(intent) ?: LocalDate.now().year
        val monthValue = getMonthValueFromIntent(intent) ?: LocalDate.now().monthValue
        switchToMonthView(year, monthValue)
    }

    private fun getSelectedYear() = yearSpinner.selectedItemPosition + 1900
    private fun setSelectedYear(year: Int) = yearSpinner.setSelection(year - 1900)

    private fun switchToMonthView(year: Int, monthValue: Int) {
        val intent = Intent(this@YearActivity, MonthActivity::class.java)
        setYearToIntent(intent, year)
        setMonthValueToIntent(intent, monthValue)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivityIfNeeded(intent, 0)
    }
}
